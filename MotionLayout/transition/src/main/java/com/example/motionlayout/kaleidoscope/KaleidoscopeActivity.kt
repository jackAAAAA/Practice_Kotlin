package com.example.motionlayout.kaleidoscope

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import com.example.motionlayout.R

class KaleidoscopeActivity : Activity() {

    private var kaleidoscope: Kaleidoscope? = null
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kaleidoscope)
        button = findViewById(R.id.kaleidoscope_button)
        button.visibility = View.INVISIBLE
        Handler(Looper.myLooper()!!).postDelayed({button.performClick()}, 100)
        Handler(Looper.myLooper()!!).postDelayed({finish()}, 11500) // 返回BezierActivity
    }

    /*开始播放动画*/
    fun start(view: View) {
        kaleidoscope?.stop()
        kaleidoscope = Kaleidoscope.with(this)
            .total(999) // 爱心数量
            .duration(10000) // 爱心持续时长
            .colorRule(Kaleidoscope.RandomColorRule())
            .build()
        kaleidoscope?.start()
    }

    private fun stop() {
        kaleidoscope?.stopSmoothly()
    }
}