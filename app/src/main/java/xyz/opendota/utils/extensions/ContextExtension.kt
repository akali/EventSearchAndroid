package xyz.opendota.utils.extensions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Context.initRecyclerView(
    recyclerView: RecyclerView,
    orientation: Int = RecyclerView.VERTICAL,
    stackFromEnd: Boolean = false
) = with(recyclerView) {
    val linearLayoutManager = LinearLayoutManager(this@initRecyclerView, orientation, false)
    linearLayoutManager.stackFromEnd = stackFromEnd
    linearLayoutManager.reverseLayout = stackFromEnd
    layoutManager = linearLayoutManager
    setHasFixedSize(true)
    setItemViewCacheSize(20)
}

