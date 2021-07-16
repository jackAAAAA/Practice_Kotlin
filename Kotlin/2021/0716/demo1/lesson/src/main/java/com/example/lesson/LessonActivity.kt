package com.example.lesson

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.core.BaseView
import com.example.lesson.entity.Lesson

class LessonActivity : AppCompatActivity(), BaseView<LessonPresenter>,
    Toolbar.OnMenuItemClickListener {

    private val lessonPresenter: LessonPresenter = LessonPresenter(this)
    override fun getPresenter(): LessonPresenter {
        return lessonPresenter
    }

    private val lessonAdapter: LessonAdapter = LessonAdapter()
    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lesson)
        toolbar.setOnMenuItemClickListener(this)

        val recyclerView: RecyclerView = findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = lessonAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this,
        LinearLayout.VERTICAL))

        refreshLayout = findViewById(R.id.swipe_refresh_layout)
        refreshLayout?.setOnRefreshListener { presenter.fetchData() }
        refreshLayout?.isRefreshing = true

        presenter.fetchData()
    }

    fun showResult(lessons: List<Lesson>) {
        lessonAdapter.updateAndNotify(lessons)
        refreshLayout?.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        presenter.showPlayback()
        return false
    }

}