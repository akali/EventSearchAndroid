package xyz.opendota.ui.profile.statisctics


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import xyz.opendota.R
import xyz.opendota.model.StatisticsInfo
import xyz.opendota.utils.AppConstants

/**
 * A simple [Fragment] subclass.
 */
class StatiscticFragment : Fragment() {

    private lateinit var tvNumberOfPerformances: TextView
    private lateinit var btnDetails: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statisctic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
    }

    private fun bindViews(view: View) {
        tvNumberOfPerformances = view.findViewById(R.id.tvNumberOfPerformances)
        btnDetails = view.findViewById(R.id.btnDetails)

        btnDetails.setOnClickListener {

            findNavController().navigate(R.id.action_details_statistic)
        }
    }

    private fun setData() {
    }

}
