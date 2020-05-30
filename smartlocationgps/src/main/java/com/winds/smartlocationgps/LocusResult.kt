package com.winds.smartlocationgps


import android.location.Location



/**
 * Represents states of LocusResult library
 * */
class LocusResult private constructor(
    val location: Location? = null,
    val error: Throwable? = null
) {
    companion object {
        internal fun error(error: Throwable) = LocusResult(error = error)
        internal fun success(location: Location) = LocusResult(location = location)
    }
}