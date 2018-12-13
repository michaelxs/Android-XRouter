package com.blue.xrouter

import android.os.Bundle
import android.os.Parcelable
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

/**
 * Created by blue on 2018/10/30.
 */
class XRouterResult private constructor(builder: Builder) {
    private var data: Bundle = Bundle()
    private var obj: Any? = null

    init {
        this.data = builder.getData()
        this.obj = builder.getObj()
    }

    fun getObj() = obj

    fun getData() = data

    class Builder {
        private var data: Bundle = Bundle()
        private var obj: Any? = null

        fun obj(obj: Any?): Builder {
            this.obj = obj
            return this
        }

        fun data(key: String, value: Byte): Builder {
            data.putByte(key, value)
            return this
        }

        fun data(key: String, value: Short): Builder {
            data.putShort(key, value)
            return this
        }

        fun data(key: String, value: Int): Builder {
            data.putInt(key, value)
            return this
        }

        fun data(key: String, value: Long): Builder {
            data.putLong(key, value)
            return this
        }

        fun data(key: String, value: Float): Builder {
            data.putFloat(key, value)
            return this
        }

        fun data(key: String, value: Double): Builder {
            data.putDouble(key, value)
            return this
        }

        fun data(key: String, value: Boolean): Builder {
            data.putBoolean(key, value)
            return this
        }

        fun data(key: String, value: String?): Builder {
            data.putString(key, value)
            return this
        }

        fun data(key: String, value: CharSequence?): Builder {
            data.putCharSequence(key, value)
            return this
        }

        fun data(key: String, value: Parcelable?): Builder {
            data.putParcelable(key, value)
            return this
        }

        fun data(key: String, value: Serializable?): Builder {
            data.putSerializable(key, value)
            return this
        }

        fun data(bundle: Bundle?): Builder {
            bundle?.let { data.putAll(it) }
            return this
        }

        fun build(): XRouterResult {
            return XRouterResult(this)
        }

        fun getObj() = obj

        fun getData() = data
    }

    override fun toString(): String {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("data", data)
            obj?.let { jsonObject.put("any", it) }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return jsonObject.toString(4)
    }
}