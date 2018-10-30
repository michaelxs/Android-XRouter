package com.blue.hellorouter

import android.content.Context
import com.blue.xrouter.XRouterInterceptor
import com.blue.xrouter.XRouterInterceptorCallback

/**
 * Created by blue on 2018/10/30.
 */
class LoginInterceptor : XRouterInterceptor {

    override fun onInit(context: Context) {
        // init something
    }

    override fun onIntercept(callback: XRouterInterceptorCallback) {
        // check login status
        callback.onContinue()
//        callback.onInterrupt("check login error")
    }

}