package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.core.utils.toast
import com.example.lesson.LessonActivity

//Tomorrow TO DO：1. 2.
//1.
//LessonAdapter.java
//2.
// 总结在Kotlin中声明八种基本数据类型数组的方式
//private val testList = arrayOf(1, 2, 3)
//        val test: FloatArray = floatArrayOf(1f, 2f)
//        val test3: Array<Float> = arrayOf(1f, 2f)
//        val test1: Array<String> = codeList
//        val bo: BooleanArray = booleanArrayOf(true, false)

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val usernameKey: String = "username"
    private val passwordKey: String = "password"

    private var et_username: EditText? = null
    private var et_password: EditText? = null
    private var et_code: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)
        et_code = findViewById(R.id.et_code)

        et_username?.setText(CacheUtils.get(usernameKey))
        et_password?.setText(CacheUtils.get(passwordKey))

        val btn_login: Button = findViewById(R.id.btn_login)
        btn_login.setOnClickListener(this)
        val img_code: CodeView = findViewById(R.id.code_view)
        img_code.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v is CodeView) {
            val codeView: CodeView = v;
            codeView.updateCode()
        } else if (v is Button) {
            login()
        }
    }

    private fun login() {
        val username = et_username?.text.toString()
        val password = et_password?.text.toString()
        val code = et_code?.text.toString()

        val user: User = User(username, password, code)
        if (verify(user)) {
            CacheUtils.save(usernameKey, username)
            CacheUtils.save(passwordKey, password)
            startActivity(Intent(this, LessonActivity::class.java))
        }
    }

    private fun verify(user: User): Boolean {
        if (user.username?.length ?: 0 < 4) {
            toast("用户名不合法")
            return false
        }
        if (user.password?.length ?: 0 < 4) {
            toast("密码不合法")
            return false
        }
        return true
    }
}