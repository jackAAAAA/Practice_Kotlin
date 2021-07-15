package com.example.core

import android.app.Application
import android.content.Context

class BaseApplication : Application() {
    companion object {
        var currentApplication: Context? = null
        fun currentApplication(): Context {
            return currentApplication!!
        }
    }
    override fun attachBaseContext(base: Context): Unit {
        super.attachBaseContext(base)
        currentApplication = this
    }

}