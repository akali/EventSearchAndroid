package xyz.opendota.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.opendota.api.AuthApi
import xyz.opendota.api.EventsApi
import xyz.opendota.data.LoginDataSource
import xyz.opendota.data.LoginRepository
import xyz.opendota.repositories.EventRepositoryImpl
import xyz.opendota.repositories.IEventRepository
import xyz.opendota.repositories.ILocalRepository
import xyz.opendota.repositories.LocalStorageImpl
import xyz.opendota.ui.home.HomeViewModel
import xyz.opendota.ui.login.LoginViewModel
import xyz.opendota.ui.profile.ProfileViewModel
import xyz.opendota.utils.AppConstants.BASE_URL
import xyz.opendota.utils.AppConstants.TOKEN_KEY
import java.util.*
import java.util.concurrent.TimeUnit

fun createHttpClient(sp: SharedPreferences): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .protocols(Collections.singletonList(Protocol.HTTP_1_1))
        .cache(null)
        .addNetworkInterceptor { chain ->
            when(val token = sp.getString(TOKEN_KEY, null)) {
                null, "" -> chain.proceed(chain.request())
                else -> {
                    // Wow, smart type check
                    // token is not null
                    val request = chain.request()
                    val newRequest = request.newBuilder()
                        .header("Authorization", token)
                        .method(request.method(), request.body())
                        .build()
                    Log.d("AppModules", request.headers().toString())
                    chain.proceed(newRequest)
                }
            }
        }
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun createEventsApiService(retrofit: Retrofit): EventsApi {
    return retrofit.create(EventsApi::class.java)
}

fun createAuthApiService(retrofit: Retrofit): AuthApi {
    return retrofit.create(AuthApi::class.java)
}

val networkModule = module {
    single { createGson() }
    single { createSharedPreference(androidContext()) }
    single { createHttpClient(get()) }
    single { createRetrofit(get(), get()) }
    single { createEventsApiService(get()) }
    single { createAuthApiService(get()) }

    single { LocalStorageImpl(get()) as ILocalRepository }
    single { EventRepositoryImpl(get(), get(), get()) as IEventRepository }

    single { LoginRepository(get()) }
    single { LoginDataSource(get()) }
}

val viewModelModule = module(override = true) {
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
}

fun <T> applySchedulersSingle(): SingleTransformer<T, T> {
    return SingleTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun createGson(): Gson = GsonBuilder()
    .setLenient()
    .create()

fun createSharedPreference(context: Context): SharedPreferences {
    return context.getSharedPreferences("localDB", Context.MODE_PRIVATE)
}

val appModules = listOf(networkModule, viewModelModule)

