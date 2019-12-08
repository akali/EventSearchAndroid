package xyz.opendota.ui.home

import android.widget.Button
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xyz.opendota.R
import xyz.opendota.utils.extensions.loadImage
import xyz.opendota.model.Event
import xyz.opendota.utils.extensions.setTextAsync


class EventAdapter : BaseQuickAdapter<Event, BaseViewHolder>(R.layout.item_event) {

    override fun convert(helper: BaseViewHolder, item: Event?) {
        helper.apply {
            setTextAsync(R.id.tvTitle, item?.title)
            setTextAsync(R.id.tvDescription, item?.description)
            item?.imageUrl?.let { getView<ImageView>(R.id.eventImage).loadImage(it, mContext) }
        }
    }

    override fun replaceData(data: MutableCollection<out Event>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun setSortedData(data: MutableList<Event>) {
        if (data.size > 0) {
            val listOfEvents = data.sortedWith(compareBy { it.title }) as MutableList<Event>
            replaceData(listOfEvents)
        } else {
            replaceData(arrayListOf())
        }
    }
}
