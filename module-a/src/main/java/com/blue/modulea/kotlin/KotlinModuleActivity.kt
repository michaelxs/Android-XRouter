package com.blue.modulea.kotlin

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.blue.modulea.R
import com.blue.modulea.databinding.ModuleaActivityMainBinding
import com.blue.xrouter.XRouter
import com.blue.xrouter.XRouterCallback
import com.blue.xrouter.XRouterResult
import com.blue.xrouter.annotation.Router
import kotlinx.android.synthetic.main.modulea_activity_main.*

/**
 * Created by blue on 2018/9/29.
 */
@Router("www.KotlinModuleActivity.com")
class KotlinModuleActivity : AppCompatActivity() {

    private lateinit var binding: ModuleaActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.modulea_activity_main)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getListData())
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    XRouter.with(this)
                            .target("www.ModuleBActivity.com")
                            .intentFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            .jump()
                }
                1 -> {
                    XRouter.with(this)
                            .target("hello.world")
                            .transition(android.R.anim.fade_in, android.R.anim.fade_out)
                            .jump()
                }
                2 -> {
                    XRouter.with(this)
                            .target("www.ModuleBActivity.com?name=tom&age=24")
                            .jump()
                }
                3 -> {
                    XRouter.with(this)
                            .target("hello.world")
                            .data("name", "blue")
                            .data("age", 18)
                            .jump()
                }
                4 -> {
                    XRouter.with(this)
                            .target("www.google.com")
                            .jump(object : XRouterCallback() {
                                override fun onRouterSuccess(routerResult: XRouterResult) {
                                }

                                override fun onRouterError(routerResult: XRouterResult) {
                                    Toast.makeText(this@KotlinModuleActivity, "target activity is not find", Toast.LENGTH_SHORT).show()
                                }
                            })
                }
                5 -> {
                    XRouter.with(this)
                            .target("www.ModuleBActivity.com")
                            .requestCode(1001)
                            .jump()
                }
                6 -> {
                    XRouter.with(this)
                            .target("toast_kotlin")
                            .route()
                }
                7 -> {
                    XRouter.with(this)
                            .target("getSum_kotlin")
                            .data("a", 1)
                            .data("b", 2)
                            .obj(Fragment())
                            .route(object : XRouterCallback() {
                                override fun onRouterSuccess(routerResult: XRouterResult) {
                                    binding.tv.text = String.format("getSum() result is : %s", routerResult.getData().getInt("result"))
                                }
                            })
                }
                8 -> {
                    val result = XRouter.with(this).target("getFragment_kotlin").route()
                    result.getObj()?.let {
                        if (it is Fragment) {
                            binding.tv.text = String.format("getFragment() result is : %s", it)
                        }
                    }
                }
            }
        }
    }

    private fun getListData(): MutableList<String> {
        val data = mutableListOf<String>()
        data.add("route ModuleBActivity\nby \"www.RouterBActivity.com\"")
        data.add("route ModuleBActivity\nby \"hello.world\" with transition")
        data.add("route ModuleBActivity with data in url")
        data.add("route ModuleBActivity with data in bundle")
        data.add("route ModuleBActivity with callback")
        data.add("route ModuleBActivity for result")
        data.add("route method async : toast()")
        data.add("route method async : getSum()\nwith params and callback")
        data.add("route method sync : getFragment()")
        return data
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "finish for result", Toast.LENGTH_SHORT).show()
        }
    }
}