package com.example.wxchatdemo.kotlin

import android.app.Application

class MyApplication : Application() {
    companion object {
        lateinit var application: Application
    }

    init {
        application = this
    }
}