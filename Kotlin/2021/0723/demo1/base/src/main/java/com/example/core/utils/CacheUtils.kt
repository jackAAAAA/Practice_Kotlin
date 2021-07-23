package com.example.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.core.BaseApplication
import com.example.core.R

@SuppressLint("StaticFieldLeak")
object CacheUtils {
    private val context : Context = BaseApplication.currentApplication()
    private val SP : SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name), Context.MODE_PRIVATE
    )
@JvmStatic
     fun save(key : String, value : String) {
        SP.edit().putString(key, value).apply()
    }
@JvmStatic
     fun get(key : String): String? {
        return SP.getString(key, null)
    }

}