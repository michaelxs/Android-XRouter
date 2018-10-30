package com.blue.xrouter

/**
 * Created by blue on 2018/9/29.
 */
abstract class XRouterCallback {

    /**
     * data bundle limit 1Mb
     */
    abstract fun onRouterSuccess(routerResult: XRouterResult)

    open fun onRouterError(routerResult: XRouterResult) {
    }
}