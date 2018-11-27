package com.blue.xrouter

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

/**
 * Created by blue on 2018/9/30.
 */
class XRouterConfig(
        val context: Context
) {

    private var target = ""

    private var requestCode = -1

    private var intentFlags = -1

    private var enterAnim = -1
    private var exitAnim = -1

    private var obj: Any? = null

    private var data = Bundle()

    fun target(target: String): XRouterConfig {
        this.target = target
        return this
    }

    fun requestCode(requestCode: Int): XRouterConfig {
        this.requestCode = requestCode
        return this
    }

    fun intentFlags(intentFlags: Int): XRouterConfig {
        this.intentFlags = intentFlags
        return this
    }

    fun transition(enterAnim: Int, exitAnim: Int): XRouterConfig {
        this.enterAnim = enterAnim
        this.exitAnim = exitAnim
        return this
    }

    fun obj(obj: Any): XRouterConfig {
        this.obj = obj
        return this
    }

    fun data(key: String, value: Byte): XRouterConfig {
        data.putByte(key, value)
        return this
    }

    fun data(key: String, value: Short): XRouterConfig {
        data.putShort(key, value)
        return this
    }

    fun data(key: String, value: Int): XRouterConfig {
        data.putInt(key, value)
        return this
    }

    fun data(key: String, value: Long): XRouterConfig {
        data.putLong(key, value)
        return this
    }

    fun data(key: String, value: Float): XRouterConfig {
        data.putFloat(key, value)
        return this
    }

    fun data(key: String, value: Double): XRouterConfig {
        data.putDouble(key, value)
        return this
    }

    fun data(key: String, value: Boolean): XRouterConfig {
        data.putBoolean(key, value)
        return this
    }

    fun data(key: String, value: String): XRouterConfig {
        data.putString(key, value)
        return this
    }

    fun data(key: String, value: CharSequence): XRouterConfig {
        data.putCharSequence(key, value)
        return this
    }

    fun data(key: String, value: Parcelable): XRouterConfig {
        data.putParcelable(key, value)
        return this
    }

    fun data(key: String, value: Serializable): XRouterConfig {
        data.putSerializable(key, value)
        return this
    }

    fun data(data: Bundle): XRouterConfig {
        this.data.putAll(data)
        return this
    }

    @JvmOverloads
    fun jump(routerCallback: XRouterCallback? = null) {
        XRouter.jump(this, routerCallback)
    }

    @JvmOverloads
    fun call(routerCallback: XRouterCallback? = null) {
        XRouter.call(this, routerCallback)
    }

    fun get() = XRouter.get(this)

    fun getTarget() = target

    fun getRequestCode() = requestCode

    fun getIntentFlags() = intentFlags

    fun getEnterAnim() = enterAnim

    fun getExitAnim() = exitAnim

    fun getObj() = obj

    fun getData() = data

    override fun toString(): String {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("target", target)
            jsonObject.put("requestCode", requestCode)
            jsonObject.put("intentFlags", intentFlags)
            jsonObject.put("enterAnim", enterAnim)
            jsonObject.put("exitAnim", exitAnim)
            jsonObject.put("data", data)
            obj?.let { jsonObject.put("any", it) }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return jsonObject.toString(4)
    }
}
