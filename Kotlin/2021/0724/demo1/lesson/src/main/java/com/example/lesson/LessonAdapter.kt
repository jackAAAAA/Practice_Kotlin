package com.example.lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.BaseViewHolder
import com.example.lesson.entity.Lesson

class LessonAdapter : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>(){

    private var list: List<Lesson> = ArrayList<Lesson>()

    fun updateAndNotify(list: List<Lesson>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder.onCreate(parent)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.onBind(list.get(position))
    }

    class LessonViewHolder constructor(itemView : View) : BaseViewHolder(itemView) {
        fun onBind(lesson: Lesson) {
            val date: String? = lesson.date
            setText(R.id.tv_date, date ?: "日期待定")
            setText(R.id.tv_content, lesson.content)
            val state: Lesson.State = lesson.state
            if (state != null) {
                setText(R.id.tv_state, state.stateName())
                var colorRes: Int = R.color.playback
                colorRes = when (state) {
                    Lesson.State.PLAYBACK -> {
                        R.color.playback
                    }
                    Lesson.State.LIVE -> R.color.live
                    Lesson.State.WAIT -> R.color.wait
                }
                val backgroundColor = itemView.context.getColor(colorRes)
                getView<View>(R.id.tv_state).setBackgroundColor(backgroundColor)
            }

        }

        companion object {
            fun onCreate(parent: ViewGroup) : LessonViewHolder {
                return LessonViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_lesson, parent, false))
            }
        }
    }

}