package com.winds.smartlocationgps

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Responsible for starting and stopping location updates=
 * @property isRequestOngoing AtomicBoolean which indicates that whether a location request is ongoing or not
 * @property mFusedLocationProviderClient (com.google.android.gms.location.FusedLocationProviderClient..com.google.android.gms.location.FusedLocationProviderClient?) used to request location
 * @constructor
 */
internal class LocationProvider(context: Context) {

    private val pendingIntent: PendingIntent by lazy {
        LocationBroadcastReceiver.getPendingIntent(context)
    }
    private val isRequestOngoing = AtomicBoolean().apply { set(false) }
    private val mFusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     * Starts continuous location tracking using FusedLocationProviderClient
     *
     * If somehow continuous location retrieval fails then it tries to retrieve last known location.
     * */
    @SuppressLint("MissingPermission")
    internal fun startUpdates(request: LocationRequest) {
        if (isRequestOngoing.getAndSet(true)) return
        logDebug("Starting location updates")
        mFusedLocationProviderClient.requestLocationUpdates(request, pendingIntent)
            ?.addOnFailureListener { e ->
                logError(e)
                logDebug("Continuous location updates failed, retrieving last known location")
                mFusedLocationProviderClient.lastLocation?.addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    it.result?.let { location ->
                        locationLiveData.postValue(LocusResult.success(location))
                    }
                }?.addOnFailureListener {
                    locationLiveData.postValue(LocusResult.error(error = it))
                }
            }
    }

    /**
     * Initiates process to retrieve single location update
     * @param request LocationRequest instance that will be used to get location
     * @param onUpdate Called on success/failure result of the single update retrieval process
     */
    internal fun getSingleUpdate(
        request: LocationRequest,
        onUpdate: (LocusResult) -> Unit
    ) {
        fun startUpdates() {
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult?) {
                    result?.lastLocation?.let { location ->
                        onUpdate(LocusResult.success(location))
                        mFusedLocationProviderClient.removeLocationUpdates(this)
                    }
                }
            }
            mFusedLocationProviderClient.requestLocationUpdates(
                request.apply { numUpdates = 1 },
                callback,
                Looper.getMainLooper()
            ).addOnFailureListener { error ->
                logError(error)
                onUpdate(LocusResult.error(error = error))
            }
        }
        mFusedLocationProviderClient.lastLocation?.addOnSuccessListener { result ->
            result?.let { location ->
                onUpdate(LocusResult.success(location))
            } ?: startUpdates()
        }?.addOnFailureListener {
            logError(it)
            logDebug("Looks like last known location is not available, requesting a new location update")
            startUpdates()
        }
    }

    /**
     * Stops location tracking by removing location callback from FusedLocationProviderClient
     * */
    internal fun stopUpdates() {
        logDebug("Stopping background location updates")
        isRequestOngoing.set(false)
        locationLiveData = MutableLiveData()
        mFusedLocationProviderClient.removeLocationUpdates(pendingIntent)
    }
}