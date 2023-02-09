package com.example.wxchatdemo.kotlin

import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.wxchatdemo.Loading
import com.example.wxchatdemo.MainWeixin
import com.example.wxchatdemo.R
import com.example.wxchatdemo.tools.WorksSizeCheckUtil
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginPhone : AppCompatActivity() {

    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var user_login: TextView
    private lateinit var button: Button
    private val TAG = "LoginPhone"
    private val myHandler: MyHandler = MyHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_phone)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView: View = window.decorView
            val option: Int = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
        initViews()
        button.isEnabled = !(phone.text.toString() == "" || password.text.toString() == "")
        inputFocus()
        buttonChangeColor()
        user_login.setOnClickListener {
            val intent: Intent = Intent(this@LoginPhone, LoginUser::class.java)
            startActivity(intent)
        }
        button.setOnClickListener {
            val intent: Intent = Intent(this@LoginPhone, Loading::class.java)
            startActivity(intent)
            Thread {
                try {
                    Thread.sleep(1000)
                    httpUrlConnPost(
                        this@LoginPhone.phone.text.toString(),
                        password.text.toString()
                    )
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun initViews(): Unit {
        phone = this.findViewById(R.id.log_phone)
        password = this.findViewById(R.id.log_passwd)
        user_login = this.findViewById(R.id.user_log)
        button = this.findViewById(R.id.log_button)
    }

    private fun inputFocus(): Unit {
        phone.setOnFocusChangeListener{v, hasFocus ->
            if (hasFocus) {
                val imageView: ImageView = findViewById(R.id.login_diver1)
                imageView.setBackgroundResource(R.color.input_dvier_focus)
            } else {
                val imageView: ImageView = findViewById(R.id.login_diver1)
                imageView.setBackgroundResource(R.color.input_dvier)
            }
        }
        password.setOnFocusChangeListener{v, hasFocus ->
            if (hasFocus) {
                val imageView: ImageView = findViewById(R.id.login_diver2)
                imageView.setBackgroundResource(R.color.input_dvier_focus)
            } else {
                val imageView: ImageView = findViewById(R.id.login_diver2)
                imageView.setBackgroundResource(R.color.input_dvier)
            }
        }

    }

    private fun buttonChangeColor(): Unit {
        val textChangeListener: WorksSizeCheckUtil.textChangeListener =
            WorksSizeCheckUtil.textChangeListener(button)
        textChangeListener.addAllEditText(phone, password)
        WorksSizeCheckUtil.setChangeListener{isHasContent ->
            if (isHasContent) {
                button.setBackgroundResource(R.drawable.login_button_focus)
                button.setTextColor(resources.getColor(R.color.loginButtonTextFouse))
            } else {
                button.setBackgroundResource(R.drawable.login_button_shape)
                button.setTextColor(resources.getColor(R.color.loginButtonText))
            }
        }
    }

    private fun httpUrlConnPost(phone: String, password: String): Unit {
        var urlConnection: HttpURLConnection? = null
        var url: URL
        try {
            // 请求的URL地地址
            url = URL(
                "http://www.gkuvision.com:8882/push/api/getNewestVersion?firmWareModel=XTUGO_Android"
            ) // 使用存放APK包的服务器地址来作为测试地址
            urlConnection = url.openConnection() as HttpURLConnection // 打开http连接
            urlConnection.setConnectTimeout(3000) // 连接的超时时间
            urlConnection.setUseCaches(false) // 不使用缓存
            // urlConnection.setFollowRedirects(false);是static函数，作用于所有的URLConnection对象。
            urlConnection.setInstanceFollowRedirects(true) // 是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
            urlConnection.setReadTimeout(3000) // 响应的超时时间
            urlConnection.setDoInput(true) // 设置这个连接是否可以写入数据
            urlConnection.setDoOutput(true) // 设置这个连接是否可以输出数据
            urlConnection.setRequestMethod("POST") // 设置请求的方式
            urlConnection.setRequestProperty(
                "Content-Type",
                "application/json;charset=UTF-8"
            ) // 设置消息的类型
            urlConnection.connect() // 连接，从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接
            val json = JSONObject() // 创建json对象
            json.put(
                "number",
                URLEncoder.encode(phone, "UTF-8")
            ) // 使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("password", URLEncoder.encode(password, "UTF-8")) // 把数据put进json对象中
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
                    buffer.append(str)
                }
                `in`.close()
                br.close()
                val rjson = JSONObject(buffer.toString())
                Log.i("aa", "rjson=$rjson") // rjson={"json":true}
                val result =
                    rjson.getBoolean("json") // 从rjson对象中得到key值为"json"的数据，这里服务端返回的是一个boolean类型的数据
                println("json:===$result")
                //如果服务器端返回的是true，则说明登录成功，否则登录失败
                if (result) { // 判断结果是否正确
                    //在Android中http请求，必须放到线程中去作请求，但是在线程中不可以直接修改UI，只能通过hander机制来完成对UI的操作
                    myHandler.sendEmptyMessage(1)
                    Log.i("用户：", "登录成功")
                } else {
                    myHandler.sendEmptyMessage(2)
                    println("222222222222222")
                    Log.i("用户：", "登录失败")
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

    fun login_activity_back(v: View): Unit {
        val intent: Intent = Intent()
        intent.setClass(this, Welcome::class.java)
        startActivity(intent)
        finish()
    }

    private inner class MyHandler : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    Log.i(TAG, msg.what.toString())
                    Toast.makeText(applicationContext, "登录成功", Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(this@LoginPhone, MainWeixin::class.java)
                    startActivity(intent)
                    finish()
                }
                2 -> {
                    Log.i(TAG, msg.what.toString())
                    AlertDialog.Builder(this@LoginPhone)
                        .setTitle("                   登录失败")
                        .setMessage("    用户名或密码错误，请重新填写")
                        .setPositiveButton("确定", null)
                        .show();
                }
            }
        }
    }

}