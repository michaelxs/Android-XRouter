package com.blue.xrouter

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by blue on 2018/10/30.
 */
class XRouterResult private constructor(builder: Builder) {
    private var data: Bundle = Bundle()
    private var obj: Any? = null

    init {
        this.data = builder.data
        this.obj = builder.obj
    }

    fun getObj() = obj

    fun getData() = data

    class Builder {
        var data: Bundle = Bundle()
        var obj: Any? = null

        fun obj(obj: Any?): Builder {
            this.obj = obj
            return this
        }

        fun data(key: String, value: Byte?): Builder {
            value?.let { data.putByte(key, it) }
            return this
        }

        fun data(key: String, value: Short?): Builder {
            value?.let { data.putShort(key, it) }
            return this
        }

        fun data(key: String, value: Int?): Builder {
            value?.let { data.putInt(key, it) }
            return this
        }

        fun data(key: String, value: Long?): Builder {
            value?.let { data.putLong(key, it) }
            return this
        }

        fun data(key: String, value: Float?): Builder {
            value?.let { data.putFloat(key, it) }
            return this
        }

        fun data(key: String, value: Double?): Builder {
            value?.let { data.putDouble(key, it) }
            return this
        }

        fun data(key: String, value: Boolean?): Builder {
            value?.let { data.putBoolean(key, it) }
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
    }
}