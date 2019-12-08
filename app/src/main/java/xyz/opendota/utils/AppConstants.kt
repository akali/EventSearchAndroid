package xyz.opendota.utils

import android.util.Base64
import android.util.Log
import xyz.opendota.R

object AppConstants {

    const val BASE_URL = "http://event-search-akali.herokuapp.com"
    const val TOKEN_KEY = "TOKEN"

    const val EVENT = "EVENT"
    const val DEFAULT_PAGE = 0
    const val PAGE_LIMIT = 10

    val rootDestinations = setOf(R.id.home_dest, R.id.news_dest, R.id.profile_dest)

    fun tokenize(credentials: String): String {
        Log.d("tokenize", credentials)
        Log.d("tokenize", Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP))
        return "Basic ${String(Base64.encode(credentials.toByteArray(), Base64.NO_WRAP))}"
    }
}
