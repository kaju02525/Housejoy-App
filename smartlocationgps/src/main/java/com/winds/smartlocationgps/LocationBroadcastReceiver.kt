package com.winds.smartlocationgps


import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.LocationResult


/**
 * Receives location broadcasts
 */
internal class LocationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_PROCESS_UPDATES = "com.winds.smartlocationgps.LocationProvider.LocationBroadcastReceiver.action.PROCESS_UPDATES"

        fun getPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, LocationBroadcastReceiver::class.java)
            intent.action = ACTION_PROCESS_UPDATES
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        logDebug("Received location update broadcast")
        intent ?: return
        if (intent.action == ACTION_PROCESS_UPDATES) {
            LocationResult.extractResult(intent)?.let { result ->
                if (result.locations.isNotEmpty()) {
                    logDebug("Received location ${result.lastLocation}")
                    locationLiveData.postValue(LocusResult.success(result.lastLocation))
                }
            }
        }
    }
}