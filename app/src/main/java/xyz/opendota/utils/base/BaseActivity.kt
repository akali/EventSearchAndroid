package xyz.opendota.utils.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

open class BaseActivity : AppCompatActivity() {
    fun <T : BaseActivity> changeActivity(activity: KClass<T>) {
        val intent = Intent(this, activity.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }
}