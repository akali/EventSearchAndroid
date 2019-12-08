package xyz.opendota.utils

import permissions.dispatcher.PermissionRequest
import xyz.opendota.ui.home.HomeFragment
import java.lang.ref.WeakReference

private val REQUEST_GETUSERLOCATION: Int = 3

private val PERMISSION_GETUSERLOCATION: Array<String> =
    arrayOf("android.permission.ACCESS_FINE_LOCATION")

fun HomeFragment.getUserLocationWithPermissionCheck() {
    if (permissions.dispatcher.PermissionUtils.hasSelfPermissions(
            activity,
            *PERMISSION_GETUSERLOCATION
        )
    ) {
        getDistance()
    } else {
        if (permissions.dispatcher.PermissionUtils.shouldShowRequestPermissionRationale(
                this,
                *PERMISSION_GETUSERLOCATION
            )
        ) {
            showLocationRequest(HomeFragmentGetUserLocationPermissionRequest(this))
        } else {
            this.requestPermissions(PERMISSION_GETUSERLOCATION, REQUEST_GETUSERLOCATION)
        }
    }
}

fun HomeFragment.onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
    when (requestCode) {
        REQUEST_GETUSERLOCATION -> {
            if (permissions.dispatcher.PermissionUtils.verifyPermissions(*grantResults)) {
                getDistance()
            }
        }
    }
}

private class HomeFragmentGetUserLocationPermissionRequest(target: HomeFragment) :
    PermissionRequest {
    private val weakTarget: WeakReference<HomeFragment> = WeakReference(target)

    override fun proceed() {
        val target = weakTarget.get() ?: return
        target.requestPermissions(PERMISSION_GETUSERLOCATION, REQUEST_GETUSERLOCATION)
    }

    override fun cancel() {
    }
}
