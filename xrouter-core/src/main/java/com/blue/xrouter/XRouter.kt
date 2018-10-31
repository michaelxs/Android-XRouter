package com.blue.xrouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blue.xrouter.tools.Logger

/**
 * router manager
 * Created by blue on 2018/9/28.
 */
object XRouter {

    private val TAG = "XRouter"

    private val pagesMapping by lazy { mutableMapOf<String, Class<out Activity>>() }
    private val methodMapping by lazy { mutableMapOf<String, XRouterMethod>() }
    private val interceptorMap by lazy { mutableMapOf<Int, XRouterInterceptor>() }
    private val interceptorPriorityList by lazy { mutableListOf<Int>() }
    private var interceptorIndex = 0

    @JvmStatic
    fun init(context: Context, isDebug: Boolean = false) {
        Logger.setDebug(isDebug)
        Logger.d(TAG, "XRouter init")
        XRouterAppInit.init()
        interceptorMap.values.forEach { it.onInit(context) }
    }

    fun registerPage(pageName: String, routerPage: Class<out Activity>) {
        Logger.d(TAG, "registerPage:$pageName")
        pagesMapping.put(pageName, routerPage)
    }

    fun registerMethod(methodName: String, routerMethod: XRouterMethod) {
        Logger.d(TAG, "registerMethod:$methodName")
        methodMapping.put(methodName, routerMethod)
    }

    fun registerInterceptor(priority: Int, routerInterceptor: XRouterInterceptor) {
        Logger.d(TAG, "registerInterceptor:${routerInterceptor.javaClass.canonicalName}(priority=$priority)")
        interceptorMap.put(priority, routerInterceptor)

        interceptorPriorityList.clear()
        interceptorPriorityList.addAll(interceptorMap.keys.sortedDescending())
    }

    /**
     * recommend
     */
    @JvmStatic
    fun with(context: Context) = XRouterConfig(context)

    /**
     * page route
     */
    fun jump(routerConfig: XRouterConfig, routerCallback: XRouterCallback? = null) {
        Logger.d(TAG, "--- start page route ---")
        Logger.d(TAG, "routerConfig:$routerConfig")
        if (interceptorMap.isNotEmpty()) {
            interceptorIndex = 0
            invokeIntercept(routerConfig, routerCallback)
        } else {
            Logger.d(TAG, "interceptorMap is empty")
            invokeJump(routerConfig, routerCallback)
        }
    }

    fun invokeIntercept(routerConfig: XRouterConfig, routerCallback: XRouterCallback? = null) {
        interceptorMap[interceptorPriorityList[interceptorIndex]]?.let {
            Logger.d(TAG, "invoke intercept:${it.javaClass.canonicalName}(priority=${interceptorPriorityList[interceptorIndex]})")
            it.onProcess(object : XRouterInterceptorCallback {
                override fun onContinue() {
                    Logger.d(TAG, "onContinue")
                    if (interceptorIndex == interceptorMap.size - 1) {
                        invokeJump(routerConfig, routerCallback)
                    } else {
                        interceptorIndex++
                        invokeIntercept(routerConfig, routerCallback)
                    }
                }

                override fun onIntercept(msg: String) {
                    Logger.w(TAG, "onIntercept:$msg")
                    routerCallback?.onRouterError(XRouterResult())
                }

            })
        } ?: routerCallback?.onRouterError(XRouterResult())
    }

    fun invokeJump(routerConfig: XRouterConfig, routerCallback: XRouterCallback? = null) {
        Logger.d(TAG, "invoke jump")
        if (routerConfig.mTarget.isNotBlank()) {
            // get only scheme+authority+path
            var page = routerConfig.mTarget
            if (routerConfig.mTarget.contains("?")) {
                page = routerConfig.mTarget.split("[?]".toRegex()).toTypedArray()[0]
            } else if (routerConfig.mTarget.contains("#")) {
                page = routerConfig.mTarget.split("[#]".toRegex()).toTypedArray()[0]
            }
            Logger.d(TAG, "page:$page")
            if (pagesMapping.containsKey(page)) {
                Logger.d(TAG, "find page success")
                val intent = Intent().apply {
                    setClass(routerConfig.context, pagesMapping[page])
                    if (routerConfig.mIntentFlags != -1) {
                        flags = routerConfig.mIntentFlags
                    }
                    if (routerConfig.context !is Activity) {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    if (page != routerConfig.mTarget) {
                        data = Uri.parse(routerConfig.mTarget)
                    }
                    putExtras(routerConfig.mData)
                }
                if (routerConfig.context is Activity) {
                    if (routerConfig.mRequestCode != -1) {
                        Logger.d(TAG, "activity startActivityForResult")
                        routerConfig.context.startActivityForResult(intent, routerConfig.mRequestCode)
                    } else {
                        Logger.d(TAG, "activity startActivity")
                        routerConfig.context.startActivity(intent)
                    }
                    if (routerConfig.mEnterAnim != -1 && routerConfig.mExitAnim != -1) {
                        Logger.d(TAG, "overridePendingTransition")
                        routerConfig.context.overridePendingTransition(routerConfig.mEnterAnim, routerConfig.mExitAnim)
                    }
                    routerCallback?.onRouterSuccess(XRouterResult())
                } else {
                    Logger.d(TAG, "context startActivity")
                    routerConfig.context.startActivity(intent)
                    routerCallback?.onRouterSuccess(XRouterResult())
                }
            } else {
                Logger.d(TAG, "find page error")
                routerCallback?.onRouterError(XRouterResult())
            }
        } else {
            Logger.d(TAG, "target is blank")
            routerCallback?.onRouterError(XRouterResult())
        }
    }

    /**
     * method route
     */
    fun call(routerConfig: XRouterConfig, routerCallback: XRouterCallback? = null) {
        Logger.d(TAG, "--- start method route ---")
        Logger.d(TAG, "routerConfig:$routerConfig")
        val targetService = methodMapping[routerConfig.mTarget]
        targetService?.let {
            Logger.d(TAG, "find method success")
            it.invoke(XRouterParams(routerConfig.context, routerConfig.mData, routerConfig.mAny, routerCallback))
        } ?: let {
            Logger.d(TAG, "find method error")
            routerCallback?.onRouterError(XRouterResult())
        }
    }

    @JvmStatic
    fun containsPage(target: String): Boolean {
        var page = target
        if (target.contains("?")) {
            page = target.split("[?]".toRegex()).toTypedArray()[0]
        } else if (target.contains("#")) {
            page = target.split("[#]".toRegex()).toTypedArray()[0]
        }
        return pagesMapping.containsKey(page)
    }

    @JvmStatic
    fun containsMethod(target: String) = methodMapping.containsKey(target)
}