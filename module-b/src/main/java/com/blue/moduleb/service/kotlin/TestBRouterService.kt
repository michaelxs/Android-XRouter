package com.blue.routerb.service.kotlin

import android.content.Context
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
    @Router("toast_kotlin")
    fun toast(context: Context, routerParams: XRouterParams): XRouterResult {
        Toast.makeText(context, "toast from other module", Toast.LENGTH_SHORT).show()
        return XRouterResult.Builder().build()
    }

    @JvmStatic
    @Router("getSum_kotlin", async = true)
    fun getSum(context: Context, routerParams: XRouterParams, callback: XRouterCallback?) {
        val fragment = routerParams.obj as Fragment
        // TODO
        val a = routerParams.data.getInt("a")
        val b = routerParams.data.getInt("b")
        val result = a + b

        callback?.onRouterSuccess(XRouterResult.Builder().data("result", result).build())
    }

    @JvmStatic
    @Router("getFragment_kotlin")
    fun getFragment(context: Context, routerParams: XRouterParams): XRouterResult {
        val fragment = Fragment()
        return XRouterResult.Builder().obj(fragment).build()
    }
}