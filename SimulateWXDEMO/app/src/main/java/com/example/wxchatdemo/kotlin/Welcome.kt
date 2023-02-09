package com.example.wxchatdemo.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.wxchatdemo.R
import com.example.wxchatdemo.Register

class Welcome : Activity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)
    }

    fun welcome_login(v: View): Unit {
        val intent: Intent = Intent()
        /*页面跳转到登录界面*/
        intent.setClass(this, LoginUser::class.java)
        startActivity(intent)
        finish()
    }

    fun welcome_register(v: View): Unit {
        val intent: Intent = Intent()
        /*页面跳转到注册界面*/
        intent.setClass(this, Register::class.java)
        startActivity(intent)
        finish()
    }
    
}