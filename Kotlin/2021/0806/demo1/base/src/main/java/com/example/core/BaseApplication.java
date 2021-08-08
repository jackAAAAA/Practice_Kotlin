package com.example.core;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

//Tomorrow TO DO：总结在Kotlin中声明八种基本数据类型数组的方式
//private val testList = arrayOf(1, 2, 3)
//        val test: FloatArray = floatArrayOf(1f, 2f)
//        val test3: Array<Float> = arrayOf(1f, 2f)
//        val test1: Array<String> = codeList
//        val bo: BooleanArray = booleanArrayOf(true, false)
public class BaseApplication extends Application {

    private static Context currentApplication;

    @NonNull
    public static Context currentApplication() {
        return currentApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        currentApplication = this;
    }
}