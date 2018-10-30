package com.blue.xrouter

import android.content.Context
import android.os.Bundle

/**
 * Created by blue on 2018/10/30.
 */
class XRouterParams(
        val context: Context,
        val data: Bundle,
        val any: Any? = null,
        val callback: XRouterCallback? = null
)