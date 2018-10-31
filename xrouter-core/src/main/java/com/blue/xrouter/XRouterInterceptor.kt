package com.blue.xrouter

import android.content.Context

/**
 * Created by blue on 2018/10/30.
 */
interface XRouterInterceptor {

    fun onInit(context: Context)

    fun onProcess(callback: XRouterInterceptorCallback)
}