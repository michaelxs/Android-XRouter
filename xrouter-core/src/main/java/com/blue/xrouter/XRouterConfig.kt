package com.blue.xrouter

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

/**
 * router config
 * Created by blue on 2018/9/30.
 */
class XRouterConfig(
        private val context: Context
) {

    var mTarget = ""

    var mRequestCode = -1

    var mIntentFlags = -1

    val mData by lazy { Bundle() }

    fun target(target: String): XRouterConfig {
        mTarget = target
        return this
    }

    fun requestCode(requestCode: Int): XRouterConfig {
        mRequestCode = requestCode
        return this
    }

    fun intentFlags(intentFlags: Int): XRouterConfig {
        mIntentFlags = intentFlags
        return this
    }

    fun data(key: String, value: Byte): XRouterConfig {
        mData.putByte(key, value)
        return this
    }

    fun data(key: String, value: Short): XRouterConfig {
        mData.putShort(key, value)
        return this
    }

    fun data(key: String, value: Int): XRouterConfig {
        mData.putInt(key, value)
        return this
    }

    fun data(key: String, value: Long): XRouterConfig {
        mData.putLong(key, value)
        return this
    }

    fun data(key: String, value: Float): XRouterConfig {
        mData.putFloat(key, value)
        return this
    }

    fun data(key: String, value: Double): XRouterConfig {
        mData.putDouble(key, value)
        return this
    }

    fun data(key: String, value: Boolean): XRouterConfig {
        mData.putBoolean(key, value)
        return this
    }

    fun data(key: String, value: String): XRouterConfig {
        mData.putString(key, value)
        return this
    }

    fun data(key: String, value: CharSequence): XRouterConfig {
        mData.putCharSequence(key, value)
        return this
    }

    fun data(key: String, value: Parcelable): XRouterConfig {
        mData.putParcelable(key, value)
        return this
    }

    fun data(key: String, value: Serializable): XRouterConfig {
        mData.putSerializable(key, value)
        return this
    }

    fun data(data: Bundle): XRouterConfig {
        mData.putAll(data)
        return this
    }

    @JvmOverloads
    fun jump(routerCallBack: XRouterCallBack? = null) {
        XRouter.jump(context, this, routerCallBack)
    }

    @JvmOverloads
    fun call(routerCallBack: XRouterCallBack? = null) {
        XRouter.call(context, this, routerCallBack)
    }

    override fun toString(): String {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("target", mTarget)
            jsonObject.put("requestCode", mRequestCode)
            jsonObject.put("intentFlags", mIntentFlags)
            jsonObject.put("data", mData)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return jsonObject.toString()
    }
}
