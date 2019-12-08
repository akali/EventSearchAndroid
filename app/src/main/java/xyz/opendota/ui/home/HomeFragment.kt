package xyz.opendota.ui.home


import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import xyz.opendota.R
import xyz.opendota.model.Event
import xyz.opendota.utils.AppConstants
import xyz.opendota.utils.custom_views.CustomDialog
import xyz.opendota.utils.extensions.initRecyclerView

class HomeFragment : Fragment() {

    private lateinit var bedroomsRV: RecyclerView

    private val viewModel: HomeViewModel by inject()
    private var eventsList: List<Event>? = null

    private val eventAdapter by lazy {
        EventAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setData()
    }

    private fun bindViews(view: View) {
        bedroomsRV = view.findViewById(R.id.eventList)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAdapter() {
        eventAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                val bundle = Bundle()
                val bedroom = getItem(position)
                bundle.putParcelable(AppConstants.EVENT, bedroom)
                view.findNavController().navigate(R.id.actionEventDetail, bundle)
            }
            setEnableLoadMore(true)
            setOnLoadMoreListener({ viewModel.getEvents() }, bedroomsRV)
        }
        activity?.initRecyclerView(bedroomsRV)
        bedroomsRV.adapter = eventAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData() {
        viewModel.getEvents()
        viewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is HomeViewModel.EventData.LoadMoreFinished -> {
                    eventAdapter.apply {
                        loadMoreComplete()
                        loadMoreEnd(true)
                    }
                }
                is HomeViewModel.EventData.Result -> {
//                    if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    eventsList = result.events
                    eventAdapter.replaceData(result.events as MutableList<Event>)
//                        getDistance()
//                    }else {
//                        getUserLocationWithPermissionCheck()
//                    }
                }
                is HomeViewModel.EventData.LoadMoreResult -> {
                    eventAdapter.apply {
                        loadMoreComplete()
                        addData(result.bedRoomInfo)
                    }
                }
                is HomeViewModel.EventData.Error -> {
                    Toast.makeText(activity, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getDistance() {
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showLocationRequest(request: PermissionRequest) {
        if (activity != null) {
            val dialog = CustomDialog(activity, R.style.PauseDialog)
            dialog.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_location_request)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                activity?.findViewById<Button>(R.id.btnCancelLocationRequest)?.setOnClickListener {
                    request.cancel()
                    cancel()
                }
                activity?.findViewById<Button>(R.id.btnApproveLocation)?.setOnClickListener {
                    request.proceed()
                    cancel()
                }
                show()
            }
        }
    }
}
