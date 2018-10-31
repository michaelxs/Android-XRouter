package com.blue.hellorouter.interceptor

import android.content.Context
import com.blue.xrouter.XRouterInterceptor
import com.blue.xrouter.XRouterInterceptorCallback
import com.blue.xrouter.annotation.RouterInterceptor

/**
 * Created by blue on 2018/10/30.
 */
@RouterInterceptor(priority = 8)
class NetworkInterceptor : XRouterInterceptor {

    override fun onInit(context: Context) {
        // init something
    }

    override fun onProcess(callback: XRouterInterceptorCallback) {
        // check network status
        callback.onContinue()
    }

}