package com.blue.hellorouter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blue.xrouter.XRouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // java demo
        btn.setOnClickListener {
            // start activity in module routera
            XRouter.with(this)
                    .target("java.RouterAActivity.com")
                    .jump()
        }

        // kotlin demo
        btn2.setOnClickListener {
            // start activity in module routera
            XRouter.with(this)
                    .target("kotlin.RouterAActivity.com")
                    .jump()
        }
    }
}
