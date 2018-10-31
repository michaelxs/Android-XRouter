package com.blue.xrouter

/**
 * Created by blue on 2018/10/30.
 */
interface XRouterInterceptorCallback {

    fun onContinue()

    fun onIntercept(msg: String)
}