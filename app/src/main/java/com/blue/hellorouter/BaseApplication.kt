package com.blue.hellorouter

import android.app.Application
import com.blue.xrouter.XRouter
import com.blue.xrouter.annotation.RouterApp

/**
 * Created by blue on 2018/10/9.
 */
@RouterApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        XRouter.registerInterceptor(5, LoginInterceptor())
        XRouter.registerInterceptor(8, LoginInterceptor2())
        XRouter.init(this, BuildConfig.DEBUG)
    }
}