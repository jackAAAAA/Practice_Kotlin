package com.example.wxchatdemo.kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.wxchatdemo.R
import com.example.wxchatdemo.adapter.findSortAdapter
import com.example.wxchatdemo.kotlin.utils.applicationContext

class FindFragment : Fragment() {

    private lateinit var listView: ListView
    private val list = mutableListOf<Map<String, String>>()
    private val pic = arrayOf<Int>(R.drawable.friend_img, R.drawable.video_img,
    R.drawable.scan_img, R.drawable.shark_img, R.drawable.look_img,
    R.drawable.search_img, R.drawable.direct_seeding_img, R.drawable.shopping_img,
    R.drawable.game_img, R.drawable.small_routine_img)
    private val pic1 = arrayOf<Int>(R.drawable.tab_img,R.drawable.tab_img,R.drawable.tab_img,R.drawable.tab_img,
        R.drawable.tab_img,R.drawable.tab_img,R.drawable.tab_img,R.drawable.tab_img,
        R.drawable.tab_img,R.drawable.tab_img)
    private val data = arrayOf<String>(
        "朋友圈        ", "视频号        ", "扫一扫        ",
        "摇一摇        ", "看一看        ", "搜一搜        ",
        "直播和附近", "购物            ", "游戏            ", "小程序        ")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.find_fragment, container, false)
        listView = view.findViewById(R.id.listView)
        initData()
        val adapter: findSortAdapter = findSortAdapter(applicationContext, list)
        listView.adapter = adapter
        return view
    }

    private fun initData(): Unit {
        for (i in data.indices) {
            val map = mutableMapOf<String, String>()
            map["pic"] = pic[i].toString()
            map["title"] = data[i].toString()
            map["pic1"] = pic1[i].toString()
            list.add(map)
        }

    }
}