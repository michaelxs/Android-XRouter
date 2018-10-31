package com.blue.hellorouter.interceptor

import android.content.Context
import com.blue.xrouter.XRouterInterceptor
import com.blue.xrouter.XRouterInterceptorCallback
import com.blue.xrouter.annotation.RouterInterceptor

/**
 * Created by blue on 2018/10/30.
 */
@RouterInterceptor()
class LoginInterceptor : XRouterInterceptor {

    override fun onInit(context: Context) {
        // init something
    }

    override fun onProcess(callback: XRouterInterceptorCallback) {
        // check login status
//        callback.onContinue()
        callback.onIntercept("check login error")
    }

}