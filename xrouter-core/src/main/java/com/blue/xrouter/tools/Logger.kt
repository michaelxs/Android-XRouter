package com.blue.xrouter.tools

import android.util.Log

/**
 * log tools
 */
object Logger {

    private val ERROR = 1
    private val WARN = 2
    private val INFO = 3
    private val DEBUG = 4
    private val VERBOSE = 5

    var LOG_LEVEL = ERROR

    fun e(tag: String, msg: String) {
        if (LOG_LEVEL >= ERROR)
            Log.e(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (LOG_LEVEL >= WARN)
            Log.w(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (LOG_LEVEL >= INFO)
            Log.i(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (LOG_LEVEL >= DEBUG)
            Log.d(tag, msg)
    }

    fun v(tag: String, msg: String) {
        if (LOG_LEVEL >= VERBOSE)
            Log.v(tag, msg)
    }

    fun setDebug(isDebug: Boolean) {
        LOG_LEVEL = if (isDebug) {
            DEBUG
        } else {
            ERROR
        }
    }
}
