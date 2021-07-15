package com.example.core.http

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class HttpClient private constructor() : OkHttpClient() {

    companion object {
        @JvmField
        val INSTANCE: HttpClient = HttpClient()
        private val gson: Gson = Gson()
        private fun <T> convert(json: String?, type: Type): T {
            return gson.fromJson(json, type)!!
        }
    }
//  作业题：
    lateinit var call: ( Request) -> Response


    fun <T> get(path: String, type: Type, entityCallback: EntityCallback<T>) {
        val request: Request = Request.Builder()
            .url("https://api.hencoder.com/$path")
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
                            json = body!!.string()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        entityCallback.onSuccess(convert<Any>(json, type) as T)
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