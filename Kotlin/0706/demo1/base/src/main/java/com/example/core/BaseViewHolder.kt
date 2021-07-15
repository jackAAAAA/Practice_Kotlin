package com.example.core

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * 只有一个构造器：
 * abstract class BaseViewHolder: RecyclerView.ViewHolder {
constructor(itemView: View) : super(itemView) {
}
}
* 等价于
 * abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
 * }
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewHashMap: MutableMap<Int, View?> = HashMap()

    @SuppressLint("UseSparesArrays")
    protected fun <T : View?> getView(@IdRes id: Int): T? {
        var view = viewHashMap[id]
        if (view == null) {
            view = itemView.findViewById(id)
            viewHashMap[id] = view
        }
        return view as T?
    }

    protected fun setText(@IdRes id: Int, text: String?) {
        (getView<View>(id) as TextView).text = text
    }

}