//有下面这行语句，可以改变在Java调用Kotlin文件中顶层函数的文件名
//@file:JvmName("KotUtils")

//这行包名不能掉，否则在其他Java文件中无法访问到此Kotlin文件中的顶层函数
package com.example.core.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import com.example.core.BaseApplication

private val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics

fun dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
}

@JvmOverloads
fun toast(string: String?, duration: Int = Toast.LENGTH_SHORT): Unit {
    Toast.makeText(BaseApplication.currentApplication(), string, duration).show()
}



