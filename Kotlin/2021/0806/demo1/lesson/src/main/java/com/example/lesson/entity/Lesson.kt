package com.example.lesson.entity

class Lesson(val date: String, val content: String, val state: State) {
    enum class State {
        PLAYBACK {
            override fun stateName(): String {
                return "右回放"
            }
        },

        LIVE {
            override fun stateName(): String {
                return "正在直播"
            }
        },

        WAIT {
            override fun stateName(): String {
                return "等待中"
            }
        };

        abstract fun stateName(): String

    }
}