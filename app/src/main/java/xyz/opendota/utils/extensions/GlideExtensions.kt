package xyz.opendota.utils.extensions

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import xyz.opendota.R

fun ImageView.loadImage(url: String, fragment: Fragment) {
    Glide
        .with(fragment)
        .load(url)
        .apply(RequestOptions().centerCrop())
        .into(this)
}


fun ImageView.loadImage(url: String, ctx: Context, placeholder: Int = R.drawable.apartment) {
    Glide
        .with(ctx)
        .load(url)
        .apply(RequestOptions().centerCrop())
        .into(this)
}
