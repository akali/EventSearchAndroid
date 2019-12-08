package xyz.opendota.repositories

import android.content.SharedPreferences
import xyz.opendota.utils.AppConstants.TOKEN_KEY

interface ILocalRepository {
    var isRegistered: Boolean
    var token: String
}

class LocalStorageImpl(
    private val sharedPreference: SharedPreferences
) : ILocalRepository {

    companion object {
        const val IS_REGISTERED = "is_registered"
    }

    override var isRegistered: Boolean
        get() = sharedPreference.getBoolean(IS_REGISTERED, false)
        set(value) {
            sharedPreference.edit().putBoolean(IS_REGISTERED, value).apply()
        }

    override var token: String
        get() = sharedPreference.getString(TOKEN_KEY, "")!!
        set(value) {
            sharedPreference.edit().putString(TOKEN_KEY, value).apply()
        }
}