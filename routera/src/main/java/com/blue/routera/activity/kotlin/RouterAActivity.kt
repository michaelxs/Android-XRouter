package com.blue.routera.activity.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.blue.routera.R
import com.blue.routera.databinding.RouteraActivityMainBinding
import com.blue.xrouter.XRouter
import com.blue.xrouter.XRouterCallBack
import com.blue.xrouter.annotation.Router
import kotlinx.android.synthetic.main.routera_activity_main.*

/**
 * Created by blue on 2018/9/29.
 */
@Router("kotlin.RouterAActivity.com")
class RouterAActivity : AppCompatActivity() {

    private lateinit var binding: RouteraActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.routera_activity_main)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getListData())
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    XRouter.with(this)
                            .target("www.RouterBActivity.com")
                            .jump()
                }
                1 -> {
                    XRouter.with(this)
                            .target("hello.world")
                            .jump()
                }
                2 -> {
                    XRouter.with(this)
                            .target("www.RouterBActivity.com?name=tom&age=24")
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
                            .jump(object : XRouterCallBack() {
                                override fun onRouterSuccess(context: Context, data: Bundle?) {
                                }

                                override fun onRouterError(context: Context) {
                                    Toast.makeText(context, "target activity is not find", Toast.LENGTH_SHORT).show()
                                }

                            })
                }
                5 -> {
                    XRouter.with(this)
                            .target("www.RouterBActivity.com")
                            .requestCode(1001)
                            .jump()
                }
                6 -> {
                    XRouter.with(this)
                            .target("toast_kotlin")
                            .call()
                }
                7 -> {
                    XRouter.with(this)
                            .target("getSum_kotlin")
                            .data("a", 1)
                            .data("b", 2)
                            .call(object : XRouterCallBack() {
                                override fun onRouterSuccess(context: Context, data: Bundle?) {
                                    binding.tv.text = String.format("getSum() result is : %s", data?.getInt("result"))
                                }
                            })
                }
                8 -> {
                    XRouter.with(this)
                            .target("getFragment_kotlin")
                            .call(object : XRouterCallBack() {
                                override fun onRouterSuccess(context: Context, data: Bundle?) {
                                }

                                override fun onRouterSuccess(context: Context, data: Bundle?, others: Any?) {
                                    others?.let {
                                        if (others is Fragment) {
                                            binding.tv.text = String.format("getFragment() result is : %s", others)
                                        }
                                    }
                                }
                            })
                }
            }
        }
    }

    private fun getListData(): MutableList<String> {
        val data = mutableListOf<String>()
        data.add("route RouterBActivity\nby \"www.RouterBActivity.com\"")
        data.add("route RouterBActivity\nby \"hello.world\"")
        data.add("route RouterBActivity with data in url")
        data.add("route RouterBActivity with data in bundle")
        data.add("route RouterBActivity with callback")
        data.add("route RouterBActivity for result")
        data.add("route method : toast()")
        data.add("route method : getSum()\nwith params and callback")
        data.add("route method : getFragment()\nwith callback")
        return data
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "finish for result", Toast.LENGTH_SHORT).show()
        }
    }
}