package com.blue.xrouter

import android.content.Context

/**
 * Created by blue on 2018/10/2.
 */
interface XRouterAsyncMethod {

    fun invoke(context: Context,
               routerParams: XRouterParams,
               callback: XRouterCallback? = null)
}