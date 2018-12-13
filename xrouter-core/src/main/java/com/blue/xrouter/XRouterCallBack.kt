package com.blue.xrouter

import com.blue.xrouter.tools.Logger

/**
 * Created by blue on 2018/9/29.
 */
open class XRouterCallback {

    /**
     * data bundle limit 1Mb
     */
    open fun onRouterSuccess(routerResult: XRouterResult) {
        Logger.d("XRouter", "routerResult:$routerResult")
    }

    open fun onRouterError(routerResult: XRouterResult) {
    }
}