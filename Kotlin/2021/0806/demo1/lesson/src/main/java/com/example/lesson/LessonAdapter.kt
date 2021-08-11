package com.example.lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.BaseViewHolder
import com.example.lesson.entity.Lesson

class LessonAdapter : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    private var list: List<Lesson> = ArrayList<Lesson>()

    fun updateAndNotify(list: List<Lesson>): Unit {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LessonAdapter.LessonViewHolder {
        return LessonViewHolder.onCreate(parent)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.onBind(list.get(position))
    }

    class LessonViewHolder(itemView: View) : BaseViewHolder(itemView) {
        companion object {
            @JvmStatic
            fun onCreate(parent: ViewGroup): LessonViewHolder {
                return LessonViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_lesson, parent, false))
            }
        }

        fun onBind(lesson: Lesson): Unit {
            setText(R.id.tv_state, lesson.date ?: "日期待定")
            setText(R.id.tv_content, lesson.content)
            val state: Lesson.State = lesson.state
            if (state != null) {
                setText(R.id.tv_state, state.stateName())
                var colorRes = when (state) {
                    Lesson.State.PLAYBACK -> {
                        R.color.playback
                    }
                    Lesson.State.LIVE -> {
                        R.color.live
                    }
                    Lesson.State.WAIT -> {
                        R.color.wait
                    }
                }
                val backgroundColor: Int = itemView.context.getColor(colorRes)
                getView<TextView>(R.id.tv_state).setBackgroundColor(backgroundColor)
            }
        }



    }


}