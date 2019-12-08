package xyz.opendota.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import xyz.opendota.R
import xyz.opendota.repositories.ILocalRepository
import xyz.opendota.ui.login.LoginActivity
import xyz.opendota.utils.base.BaseActivity

class ProfileFragment : Fragment() {

    private lateinit var btnLogout: Button
    private lateinit var tvNameProfile: TextView

    private val viewModel: ProfileViewModel by inject()
    private val localStorage: ILocalRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
    }

    private fun bindViews(view: View) {
        tvNameProfile = view.findViewById(R.id.tvNameProfile)
        btnLogout = view.findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener {
            localStorage.token = ""
            (activity as BaseActivity).changeActivity(LoginActivity::class)
        }
    }

    private fun setData() {
        viewModel.getProfileInfo()
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is ProfileViewModel.ProfileData.ShowLoading -> { }
                is ProfileViewModel.ProfileData.HideLoading -> { }
                is ProfileViewModel.ProfileData.Result -> {
                    tvNameProfile.text = "Welcome ${it.profileInfo.name}"
                }
                is ProfileViewModel.ProfileData.Error -> { }
            }
        })
    }
}
