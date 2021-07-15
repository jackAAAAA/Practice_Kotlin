package com.example.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.core.BaseApplication
import com.example.core.R

@SuppressLint("StaticFieldLeak")
class CacheUtils {
    companion object {
        private var context: Context = BaseApplication.currentApplication()
        private val SP: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE)
        fun save(key: String, value: String): Unit {
            SP.edit().putString(key, value).apply()
        }
        fun get(key: String): String? {
            return SP.getString(key, null)
        }
    }
}