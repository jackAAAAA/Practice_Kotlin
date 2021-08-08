package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient
import com.example.core.utils.Utils
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class LessonPresenter constructor(val activity: LessonActivity)  {
    private val LESSON_PATH: String = "lessons"
    private var lessons: List<Lesson> = ArrayList<Lesson>()
    private val type : Type = object : TypeToken<List<Lesson>>() {
    }.type

    fun fetchData() {
        HttpClient.INSTANCE.get(LESSON_PATH, type, object : EntityCallback<List<Lesson>> {
            override fun onSuccess(entity: List<Lesson>) {
                this@LessonPresenter.lessons = entity
                this@LessonPresenter.activity.runOnUiThread(object : Runnable {
                    override fun run() {
                        this@LessonPresenter.activity.showResult(lessons)
                    }
                })
            }

            override fun onFailure(message: String?) {
                activity.runOnUiThread { Utils.toast(message!!)}
            }
        })
    }

    fun showPlayback() {
        var playbackLessons: MutableList<Lesson> = ArrayList<Lesson>()
        for (lesson in lessons) {
            if (lesson.state == Lesson.State.PLAYBACK) {
                playbackLessons.add(lesson)
            }
        }
        activity.showResult(playbackLessons)
    }
}