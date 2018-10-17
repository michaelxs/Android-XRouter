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

    @JvmStatic
    fun init(isDebug: Boolean = false) {
        Logger.setDebug(isDebug)
        Logger.d(TAG, "XRouter init")
        XRouterAppInit.init()
    }

    fun registerPage(pageName: String, routerPage: Class<out Activity>) {
        Logger.d(TAG, "registerPage:$pageName")
        pagesMapping.put(pageName, routerPage)
    }

    fun registerMethod(methodName: String, routerMethod: XRouterMethod) {
        Logger.d(TAG, "registerMethod:$methodName")
        methodMapping.put(methodName, routerMethod)
    }

    /**
     * recommend
     */
    @JvmStatic
    fun with(context: Context) = XRouterConfig(context)

    /**
     * page route
     */
    fun jump(context: Context, routerConfig: XRouterConfig, routerCallBack: XRouterCallBack? = null) {
        Logger.d(TAG, "start page route")
        Logger.d(TAG, "routerConfig:$routerConfig")
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
                    setClass(context, pagesMapping[page])
                    if (routerConfig.mIntentFlags != -1) {
                        flags = routerConfig.mIntentFlags
                    }
                    if (context !is Activity) {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    if (page != routerConfig.mTarget) {
                        data = Uri.parse(routerConfig.mTarget)
                    }
                    putExtras(routerConfig.mData)
                }
                if (routerConfig.mRequestCode != -1 && context is Activity) {
                    Logger.d(TAG, "startActivityForResult")
                    context.startActivityForResult(intent, routerConfig.mRequestCode)
                    routerCallBack?.onRouterSuccess(context)
                } else {
                    Logger.d(TAG, "startActivity")
                    context.startActivity(intent)
                    routerCallBack?.onRouterSuccess(context)
                }
            } else {
                Logger.d(TAG, "find page error")
                routerCallBack?.onRouterError(context)
            }
        } else {
            Logger.d(TAG, "target is blank")
            routerCallBack?.onRouterError(context)
        }
    }

    /**
     * method route
     */
    fun call(context: Context, routerConfig: XRouterConfig, routerCallBack: XRouterCallBack? = null) {
        Logger.d(TAG, "start method route")
        Logger.d(TAG, "routerConfig:$routerConfig")
        val targetService = methodMapping[routerConfig.mTarget]
        targetService?.let {
            Logger.d(TAG, "find method success")
            it.invoke(context, routerConfig.mData, routerCallBack)
        } ?: let {
            Logger.d(TAG, "find method error")
            routerCallBack?.onRouterError(context)
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