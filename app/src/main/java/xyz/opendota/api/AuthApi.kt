package xyz.opendota.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import xyz.opendota.model.ProfileResponse
import xyz.opendota.model.RegistrationRequest
import xyz.opendota.model.RegistrationResponse

interface AuthApi {
    @GET("/users/me")
    fun me(@Header("Authorization") token: String): Single<ProfileResponse>

    @POST("/users/register")
    fun register(form: RegistrationRequest): Single<RegistrationResponse>
}
