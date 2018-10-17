package com.blue.routerb.activity

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blue.routerb.R
import com.blue.routerb.databinding.RouterbActivityMainBinding
import com.blue.xrouter.annotation.Router

/**
 * Created by blue on 2018/9/29.
 */
@Router("www.RouterBActivity.com", "hello.world")
class RouterBActivity : AppCompatActivity() {

    private lateinit var binding: RouterbActivityMainBinding

    private val name by lazy { intent.data?.getQueryParameter("name") ?: intent.getStringExtra("name") }
    private val age by lazy {
        intent.data?.let {
            it.getQueryParameter("age")?.toInt() ?: -1
        } ?: let {
            intent.getIntExtra("age", -1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.routerb_activity_main)

        binding.result = "params : {name : $name , age : $age}"

        binding.btn.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}