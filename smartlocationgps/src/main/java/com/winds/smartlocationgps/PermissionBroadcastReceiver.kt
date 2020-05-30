package com.winds.smartlocationgps
import androidx.lifecycle.Observer

/**
 * Receives results related to permission model
 */

class PermissionObserver(private val onResult: (Throwable?) -> Unit) : Observer<String> {

    override fun onChanged(status: String?) {
        logDebug("Received Permission broadcast")
        status ?: return
        isRequestingPermission.set(false)
        when (status) {
            Constants.GRANTED -> {
                logDebug("Permission granted")
                onResult(null)
            }
            else -> {
                logDebug(status)
                onResult(Throwable(status))
            }
        }
        permissionLiveData.removeObserver(this)
        permissionLiveData.postValue(null)
    }

}