package xyz.opendota.ui.home.event_details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import org.koin.android.ext.android.inject
import xyz.opendota.R
import xyz.opendota.model.Event
import xyz.opendota.ui.home.HomeViewModel
import xyz.opendota.utils.AppConstants
import xyz.opendota.utils.extensions.loadImage

class EventDetailFragment : Fragment() {

    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var eventImage: ImageView

    private val viewModel: HomeViewModel by inject()
    private var event: Event? = null

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        if (args?.containsKey(AppConstants.EVENT) == true) {
            event = args.getParcelable(AppConstants.EVENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
    }

    private fun bindViews(view: View) {
        tvTitle = view.findViewById(R.id.tvTitle)
        tvDescription = view.findViewById(R.id.tvDescription)
        eventImage = view.findViewById(R.id.eventImage)
    }

    private fun setData() {
        tvTitle.text = event?.title
        tvDescription.text = event?.description
        event?.imageUrl?.let { eventImage.loadImage(it, this) }

        viewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is HomeViewModel.EventData.ResultBooked -> {
                    Toast.makeText(
                        activity,
                        "${event?.title} successfully booked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
