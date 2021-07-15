package com.example.core.http

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

class HttpClient private constructor() : OkHttpClient() {
    operator fun <T> get(path: String, type: Type, entityCallback: EntityCallback<T>) {
        val request = Request.Builder()
            .url("https://api.hencoder.com/$path")
            .build()
        val call = INSTANCE.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                entityCallback.onFailure("网络异常")
            }

            override fun onResponse(call: Call, response: Response) {
                val code = response.code()
                if (code >= 200 && code < 300) {
                    val body = response.body()
                    var json: String? = null
                    try {
                        json = body!!.string()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    entityCallback.onSuccess(convert<Any>(json, type) as T)
                } else if (code >= 400 && code < 500) {
                    entityCallback.onFailure("客户端错误")
                } else if (code > 500 && code < 600) {
                    entityCallback.onFailure("服务器错误")
                } else {
                    entityCallback.onFailure("未知错误")
                }
            }
        })
    }

    companion object {
        val INSTANCE = HttpClient()
        private val gson = Gson()
        private fun <T> convert(json: String?, type: Type): T {
            return gson.fromJson(json, type)
        }
    }
}