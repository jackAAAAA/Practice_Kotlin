package com.example.wxchatdemo.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.wxchatdemo.R

class AppStart : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_start)
        Handler(Looper.myLooper()!!).postDelayed({
            val intent: Intent = Intent(this, Welcome::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

}