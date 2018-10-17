package com.blue.xrouter

import android.content.Context
import android.os.Bundle

/**
 * router method
 * Created by blue on 2018/10/2.
 */
interface XRouterMethod {

    fun invoke(
            context: Context,
            data: Bundle,
            callBack: XRouterCallBack? = null
    )
}