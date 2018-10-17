package com.blue.routerb.service.kotlin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.blue.xrouter.XRouterCallBack
import com.blue.xrouter.annotation.Router

/**
 * Created by blue on 2018/9/28.
 */
object TestBRouterService {

    @JvmStatic
    @Router("toast_kotlin")
    fun toast(context: Context, data: Bundle, callBack: XRouterCallBack?) {
        Toast.makeText(context, "toast from other module", Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    @Router("getSum_kotlin")
    fun getSum(context: Context, data: Bundle, callBack: XRouterCallBack?) {
        val a = data.getInt("a")
        val b = data.getInt("b")
        val result = a + b

        data.putInt("result", result)
        callBack?.onRouterSuccess(context, data)
    }

    @JvmStatic
    @Router("getFragment_kotlin")
    fun getFragment(context: Context, data: Bundle, callBack: XRouterCallBack?) {
        val fragment = Fragment()
        callBack?.onRouterSuccess(context, data, fragment)
    }
}