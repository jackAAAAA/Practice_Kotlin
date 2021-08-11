package com.example.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.core.BaseApplication

@SuppressLint("StaticFieldLeak")
class CacheUtils {
    companion object {
        private val context: Context = BaseApplication.currentApplication()
        private val SP: SharedPreferences =
            context.getSharedPreferences("Hencoder", Context.MODE_PRIVATE)
        fun save(key: String, value: String) {
            SP.edit().putString(key, value).apply()
        }
        fun get(key: String): String? {
            return SP.getString(key, null)
        }
    }
}