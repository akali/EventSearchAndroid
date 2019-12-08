package xyz.opendota.ui.profile

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import xyz.opendota.api.AuthApi
import xyz.opendota.di.applySchedulersSingle
import xyz.opendota.model.ProfileInfo
import xyz.opendota.repositories.ILocalRepository
import xyz.opendota.utils.base.BaseViewModel

class ProfileViewModel(private val api: AuthApi, private val localStorage: ILocalRepository) :
    BaseViewModel() {

    val liveData by lazy {
        MutableLiveData<ProfileData>()
    }

    fun getProfileInfo() {
        disposables.add(
            dataFromNetwork()
                .compose(applySchedulersSingle())
                .doOnSubscribe { liveData.value = ProfileData.ShowLoading }
                .doFinally { liveData.value = ProfileData.HideLoading }
                .subscribe(
                    { result -> liveData.value = ProfileData.Result(result) },
                    { error -> liveData.value = ProfileData.Error(error.message) }
                )
        )
    }

    private fun dataFromNetwork(): Single<ProfileInfo> {
        return api.me(localStorage.token).map { t -> ProfileInfo(t.data.username) }
    }

    sealed class ProfileData {
        object HideLoading : ProfileData()
        object ShowLoading : ProfileData()
        data class Result(val profileInfo: ProfileInfo) : ProfileData()
        data class Error(val message: String?) : ProfileData()
    }
}