package xyz.opendota.data

import io.reactivex.Single
import xyz.opendota.api.AuthApi
import xyz.opendota.model.ProfileResponse
import xyz.opendota.utils.AppConstants.tokenize

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(val api: AuthApi) {

    fun login(username: String, password: String): Single<ProfileResponse> {
        return api.me(tokenize("${username}:${password}"))
    }

    fun logout() {
        // TODO: revoke authentication
    }

    fun check(token: String): Single<ProfileResponse> {
        return api.me(token)
    }
}
