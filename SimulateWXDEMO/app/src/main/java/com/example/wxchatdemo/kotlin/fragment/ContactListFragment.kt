package com.example.wxchatdemo.kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.wxchatdemo.MainWeixin
import com.example.wxchatdemo.R
import com.example.wxchatdemo.SideBar
import com.example.wxchatdemo.SideBar.ISideBarSelectCallBack
import com.example.wxchatdemo.adapter.SortAdapter
import com.example.wxchatdemo.kotlin.utils.applicationContext
import com.example.wxchatdemo.tools.User

class ContactListFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var sideBar: SideBar
    private var list2 = mutableListOf<Int>()
    companion object {
        var list = mutableListOf<User>()
        var data = mutableListOf<Map<String, String>>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.contactlist_fragment, container, false)
        listView = view.findViewById(R.id.listView)
        sideBar = view.findViewById(R.id.side_bar)
        list = MainWeixin.list
        list2 = MainWeixin.list2
        data = MainWeixin.data
        val adapter: SortAdapter = SortAdapter(applicationContext, list, list2, data)
        listView.adapter = adapter
        sideBar.setOnStrSelectCallBack(ISideBarSelectCallBack {index, selectStr ->
            for (i in list.indices) {
                if (list[i].name == "新的朋友" || list[i].name == "群聊" ||
                    list[i].name == "标签" || list[i].name == "公众号") {
                    continue
                }
                if (selectStr.equals(list[i].firstLetter, ignoreCase = true)) {
                    listView.setSelection(i) // 选择到首字母出现的位置
                    return@ISideBarSelectCallBack
                }
            }
        })
        return view
    }
}