package com.blue.xrouter

import android.os.Bundle

/**
 * Created by blue on 2018/10/30.
 */
class XRouterResult(
        val data: Bundle? = null,
        val obj: Any? = null
) {
    constructor(data: Bundle) : this(data, null)

    constructor(obj: Any) : this(null, obj)
}