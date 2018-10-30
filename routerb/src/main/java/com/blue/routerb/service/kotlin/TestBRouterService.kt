package com.blue.routerb.service.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.blue.xrouter.XRouterParams
import com.blue.xrouter.XRouterResult
import com.blue.xrouter.annotation.Router

/**
 * kotlin provides service
 * Created by blue on 2018/9/28.
 */
object TestBRouterService {

    @JvmStatic
    @Router("toast_kotlin")
    fun toast(routerParams: XRouterParams) {
        with(routerParams) {
            Toast.makeText(context, "toast from other module", Toast.LENGTH_SHORT).show()
        }
    }

    @JvmStatic
    @Router("getSum_kotlin")
    fun getSum(routerParams: XRouterParams) {
        with(routerParams) {
            val a = data.getInt("a")
            val b = data.getInt("b")
            val result = a + b

            callback?.onRouterSuccess(XRouterResult(Bundle().apply { putInt("result", result) }))
        }
    }

    @JvmStatic
    @Router("getFragment_kotlin")
    fun getFragment(routerParams: XRouterParams) {
        with(routerParams) {
            val fragment = Fragment()
            callback?.onRouterSuccess(XRouterResult(obj = fragment))
        }
    }
}