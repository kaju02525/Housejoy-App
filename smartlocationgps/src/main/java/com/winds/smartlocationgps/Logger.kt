package com.winds.smartlocationgps

import android.util.Log

/**
 * Used to determine whether the logging is enabled or not
 */
internal var isLoggingEnabled: Boolean = false

/**
 * Used internally to log error type of log messages
 * @receiver T allows this method to access the class name of the calling class
 * @param log String is the actual log message
 */
internal inline fun <reified T : Any> T.logError(log: String) {
    if (isLoggingEnabled) {
        Log.e(this::class.java.simpleName, log)
    }
}

/**
 * Used internally to log error type of log messages
 * @receiver T allows this method to access the class name of the calling class
 * @param throwable Throwable is the actual error that needs to be logged
 */
internal inline fun <reified T : Any> T.logError(throwable: Throwable) {
    if (isLoggingEnabled) {
        Log.e(this::class.java.simpleName, throwable.message.toString())
    }
}

/**
 * Used internally to log debug type of log messages
 * @receiver T allows this method to access the class name of the calling class
 * @param log String is the actual log message
 */
internal inline fun <reified T : Any> T.logDebug(log: String) {
    if (isLoggingEnabled) {
        Log.d(this::class.java.simpleName, log)
    }
}