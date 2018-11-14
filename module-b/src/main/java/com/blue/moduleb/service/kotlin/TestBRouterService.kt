package com.blue.routerb.service.kotlin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.blue.xrouter.XRouterCallback
import com.blue.xrouter.XRouterParams
import com.blue.xrouter.XRouterResult
import com.blue.xrouter.annotation.Router

/**
 * kotlin provides service
 * Created by blue on 2018/9/28.
 */
object TestBRouterService {

    @JvmStatic
    @Router("toast_kotlin", async = true)
    fun toast(context: Context, routerParams: XRouterParams, callback: XRouterCallback?) {
        Toast.makeText(context, "toast from other module", Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    @Router("getSum_kotlin", async = true)
    fun getSum(context: Context, routerParams: XRouterParams, callback: XRouterCallback?) {
        val a = routerParams.data.getInt("a")
        val b = routerParams.data.getInt("b")
        val result = a + b

        callback?.onRouterSuccess(XRouterResult(Bundle().apply { putInt("result", result) }))
    }

    @JvmStatic
    @Router("getFragment_kotlin")
    fun getFragment(context: Context, routerParams: XRouterParams): XRouterResult {
        val fragment = Fragment()
        return XRouterResult(fragment)
    }
}