package com.example.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.example.app.R
import com.example.core.utils.Utils
import java.util.*

class CodeView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val paint = Paint()

    private val codeList: Array<String> = arrayOf(
        "kotlin",
        "android",
        "java",
        "http",
        "https",
        "okhttp",
        "retrofit",
        "tcp/ip"
    )

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        gravity = Gravity.CENTER
        setBackgroundColor(context.getColor(R.color.colorPrimary))
        setTextColor(Color.WHITE)

        //使画笔抗锯齿显示
        paint.isAntiAlias = true

        paint.style = Paint.Style.STROKE
        paint.color = context.getColor(R.color.colorAccent)
        paint.strokeWidth = Utils.dp2px(6f)

        updateCode()
    }

    fun updateCode() {
        val random: Int = Random().nextInt(codeList.size)
        val code: String = codeList[random]
        text = code
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), 0f, paint)
        super.onDraw(canvas)
    }
}