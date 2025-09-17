package himanshu.root.taskmanager.view

import androidx.recyclerview.widget.DiffUtil
import himanshu.root.taskmanager.data.local.Task

class TaskDiffUtils : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(
        oldItem: Task,
        newItem: Task
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Task,
        newItem: Task
    ): Boolean {
        val result = true
        if(oldItem.id != newItem.id) return false
        if(oldItem.title != newItem.title) return false
        if(oldItem.description != newItem.description) return false
        if(oldItem.isDone != newItem.isDone) return false
        if(oldItem.updatedAt != newItem.updatedAt) return false
        return result
    }
}