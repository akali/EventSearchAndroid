package xyz.opendota.data

import io.reactivex.Single
import xyz.opendota.data.model.LoggedInUser
import xyz.opendota.model.ProfileResponse

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): Single<ProfileResponse> {
        return dataSource.login(username, password)
    }

    private fun setLoggedInUser(profile: ProfileResponse) {
        this.user = LoggedInUser("", profile.data.username)
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    fun check(token: String): Single<ProfileResponse> {
        return dataSource.check(token)
    }
}