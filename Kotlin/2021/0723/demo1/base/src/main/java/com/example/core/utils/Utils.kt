package com.example.core.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import com.example.core.BaseApplication

object Utils {
    private val displayMetrics : DisplayMetrics = Resources.
    getSystem().displayMetrics
    @JvmStatic
    fun dp2px(dp : Float) : Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp, displayMetrics)
    }
//    待优化
//    fun toast(string : String) : Unit {
//        toast(string, Toast.LENGTH_SHORT)
//    }
    @JvmStatic
    @JvmOverloads
    fun toast(string: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(BaseApplication.currentApplication(), string, duration).show()
    }
}