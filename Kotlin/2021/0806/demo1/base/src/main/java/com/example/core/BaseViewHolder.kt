package com.example.core

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("UserSparseArrays")
    private val viewHashMap: MutableMap<Int, View> = HashMap<Int, View>()
    fun<T: View> getView(id: Int): T {
        var view: View? = viewHashMap[id]
        if (view == null) {
            view = itemView.findViewById(id)
            viewHashMap[id] = view
        }
        return view as T
    }

    fun setText(id: Int, text: String): Unit {
        (getView(id) as TextView).text = text
    }

}