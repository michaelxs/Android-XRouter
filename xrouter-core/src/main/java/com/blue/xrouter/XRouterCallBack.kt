package com.blue.xrouter

import android.content.Context
import android.os.Bundle

/**
 * router callback
 * Created by blue on 2018/9/29.
 */
abstract class XRouterCallBack {

    /**
     * bundle limit 1Mb
     */
    abstract fun onRouterSuccess(context: Context, data: Bundle? = null)

    open fun onRouterSuccess(context: Context, data: Bundle? = null, others: Any? = null) {
    }

    open fun onRouterError(context: Context) {
    }
}