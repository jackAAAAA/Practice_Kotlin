package com.example.core.http

import com.google.gson.Gson
import okhttp3.*
import okhttp3.Request.Builder
import java.io.IOException
import java.lang.reflect.Type

class HttpClient : OkHttpClient() {
    companion object {
        //   通过 @JvmField 注解可以让编译器只⽣成⼀个 public 的成员属性，不生成对应
//   的 setter/getter 函数
        @JvmField
        val INSTANCE: HttpClient = HttpClient()
        private val gson: Gson = Gson()
        private fun <T> convert(json: String, type: Type): T {
            return gson.fromJson(json, type)
        }
    }

    fun <T> get(path: String, type: Type, entityCallback: EntityCallback<T>): Unit {
        val request: Request = Request.Builder()
            .url("https://api.hencoder.com/${path}")
            .build()
        val call: Call = INSTANCE.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                entityCallback.onFailure("网络异常")
            }

            override fun onResponse(call: Call, response: Response) {
                val code: Int = response.code()
                when (code) {
                    in 200..299 -> {
                        val body: ResponseBody? = response.body()
                        var json: String? = null
                        try {
                            json = body?.string()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        entityCallback.onSuccess(convert(json!!, type) as T)
                    }
                    in 400..499 -> {
                        entityCallback.onFailure("客户端错误")
                    }
                    in 500..599 -> {
                        entityCallback.onFailure("服务器错误")
                    }
                    else -> {
                        entityCallback.onFailure("未知错误")
                    }
                }
            }
        })
    }
}