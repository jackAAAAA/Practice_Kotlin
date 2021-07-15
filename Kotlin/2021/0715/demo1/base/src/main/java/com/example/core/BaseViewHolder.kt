package com.example.core

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
abstract class BaseViewHolder(itermView: View) : RecyclerView.ViewHolder(itermView) {
    @SuppressLint("UseSparseArrays")
    private val viewHashMap: MutableMap<Int, View> =
        HashMap<Int, View>()


    protected  fun <T: View> getView(@IdRes id: Int): T {
        var view: View? = viewHashMap[id]
        if (view == null) {
            view = itemView.findViewById(id)
            viewHashMap[id] = view
        }
        return view as T
    }

    protected fun setText(@IdRes id: Int, text: String?) {
        (getView<View>(id) as TextView).text = text
    }

}