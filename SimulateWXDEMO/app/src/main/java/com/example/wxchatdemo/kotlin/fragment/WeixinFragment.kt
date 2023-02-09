package com.example.wxchatdemo.kotlin.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.wxchatdemo.R
import com.example.wxchatdemo.adapter.ImageAdapter
import com.example.wxchatdemo.kotlin.utils.applicationContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class WeixinFragment(number: String?) : Fragment() {

    private var number: String? = number
    // 声明组件
    private lateinit var listView: ListView

    // 创建集合用于存储服务器发来的显示微信消息列表的一些信息
//    集合定义的两种写法
//    private val list: MutableList<Map<String, Any>> = ArrayList()
    private val list = mutableListOf<Map<String, Any>>()

    //自定义的一个Hander消息机制
    private val myHandler = MyHandler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 开一个线程完成网络请求操作
        val thread1 = Thread { httpUrlConnPost(number.toString()) }
        thread1.start()
        /*等待网络请求线程完成*/try {
            thread1.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        //获取fragment布局
        val view: View = inflater.inflate(R.layout.weixin_fragment, container, false)
        //初始化组件
        listView = view.findViewById(R.id.listView)
        //创建自定义的适配器，用于把数据显示在组件上
        val adapter: BaseAdapter = ImageAdapter(applicationContext, list)
        //设置适配器
        listView.setAdapter(adapter)
        return view
    }

    // 1.编写一个发送请求的方法
    // 发送请求的主要方法
    fun httpUrlConnPost(number: String?) {
        var urlConnection: HttpURLConnection? = null
        val url: URL
        try {
            // 请求的URL地地址
            url = URL(
                "http://100.2.178.10:8080/AndroidServer1_war_exploded/WeixinInformation"
            )
            urlConnection = url.openConnection() as HttpURLConnection // 打开http连接
            urlConnection.connectTimeout = 3000 // 连接的超时时间
            urlConnection!!.useCaches = false // 不使用缓存
            // urlConnection.setFollowRedirects(false);是static函数，作用于所有的URLConnection对象。
            urlConnection.instanceFollowRedirects = true // 是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
            urlConnection.readTimeout = 3000 // 响应的超时时间
            urlConnection.doInput = true // 设置这个连接是否可以写入数据
            urlConnection.doOutput = true // 设置这个连接是否可以输出数据
            urlConnection.requestMethod = "POST" // 设置请求的方式
            urlConnection.setRequestProperty(
                "Content-Type",
                "application/json;charset=UTF-8"
            ) // 设置消息的类型
            urlConnection.connect() // 连接，从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接
            val json = JSONObject() // 创建json对象
            //json.put("title", URLEncoder.encode(title, "UTF-8"));// 使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("number", URLEncoder.encode(number, "UTF-8")) // 把数据put进json对象中
            val jsonstr = json.toString() // 把JSON对象按JSON的编码格式转换为字符串
            // ------------字符流写入数据------------
            val out = urlConnection.outputStream // 输出流，用来发送请求，http请求实际上直到这个函数里面才正式发送出去
            val bw =
                BufferedWriter(OutputStreamWriter(out)) // 创建字符流对象并用高效缓冲流包装它，便获得最高的效率,发送的是字符串推荐用字符流，其它数据就用字节流
            bw.write(jsonstr) // 把json字符串写入缓冲区中
            bw.flush() // 刷新缓冲区，把数据发送出去，这步很重要
            out.close()
            bw.close() // 使用完关闭
            Log.i("aa", urlConnection.responseCode.toString() + "")
            //以下判斷是否訪問成功，如果返回的状态码是200则说明访问成功
            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) { // 得到服务端的返回码是否连接成功
                // ------------字符流读取服务端返回的数据------------
                val `in` = urlConnection.inputStream
                val br = BufferedReader(
                    InputStreamReader(`in`)
                )
                var str: String? = null
                val buffer = StringBuffer()
                while (br.readLine().also { str = it } != null) { // BufferedReader特有功能，一次读取一行数据
                    println("测试：$str")
                    buffer.append(str)
                }
                `in`.close()
                br.close()
                val rjson = JSONObject(buffer.toString())
                val str1 = rjson.getJSONObject("json")["titleimg"].toString()
                val pic = str1.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val str2 = rjson.getJSONObject("json")["title"].toString()
                val title = str2.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val str3 = rjson.getJSONObject("json")["content"].toString()
                val content = str3.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val str4 = rjson.getJSONObject("json")["time"].toString()
                val time = str4.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val str5 = rjson.getJSONObject("json")["showcode"].toString()
                val pic2 = str5.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                for (i in pic.indices) {
                    val map: MutableMap<String, Any> = HashMap()
                    map["pic"] = pic[i]
                    println("网址:" + pic[i])
                    map["title"] = title[i]
                    println("网址:" + title[i])
                    map["content"] = content[i]
                    map["time"] = time[i]
                    map["code"] = pic2[i]
                    list.add(map) //将map放到list集合中
                }
                val result =
                    rjson.getBoolean("json") // 从rjson对象中得到key值为"json"的数据，这里服务端返回的是一个boolean类型的数据
                println("json:===$result")
                //如果服务器端返回的是true，则说明跳转微信页成功，跳转微信页失败
                if (result) { // 判断结果是否正确
                    //在Android中http请求，必须放到线程中去作请求，但是在线程中不可以直接修改UI，只能通过hander机制来完成对UI的操作
                    myHandler.sendEmptyMessage(1)
                    Log.i("用户：", "跳转微信页成功")
                } else {
                    myHandler.sendEmptyMessage(2)
                    println("222222222222222")
                    Log.i("用户：", "跳转微信页失败")
                }
            } else {
                myHandler.sendEmptyMessage(2)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("aa", e.toString())
            println("11111111111111111")
            myHandler.sendEmptyMessage(2)
        } finally {
            urlConnection!!.disconnect() // 使用完关闭TCP连接，释放资源
        }
    }

    // 在Android中不可以在线程中直接修改UI，只能借助Handler机制来完成对UI的操作
    internal class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> Log.i("aa", msg.what.toString() + "")
                2 -> Log.i("aa", msg.what.toString() + "")
            }
        }
    }
}