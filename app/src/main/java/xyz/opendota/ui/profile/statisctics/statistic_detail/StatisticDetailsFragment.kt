package xyz.opendota.ui.profile.statisctics.statistic_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.opendota.R

class StatisticDetailsFragment : Fragment() {

    private var nameOfField: String? = null

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        nameOfField = args?.getString("FIELD_TYPE")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistic_details, container, false)
    }


}