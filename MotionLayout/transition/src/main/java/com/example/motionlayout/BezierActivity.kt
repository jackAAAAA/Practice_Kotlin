package com.example.motionlayout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.motionlayout.kaleidoscope.Kaleidoscope
import com.example.motionlayout.kaleidoscope.KaleidoscopeActivity

class BezierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bezier)
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.myLooper()!!).postDelayed(
            {
                val intent: Intent = Intent(this, KaleidoscopeActivity::class.java)
                startActivity(intent)
            }, 10000)
    }
}