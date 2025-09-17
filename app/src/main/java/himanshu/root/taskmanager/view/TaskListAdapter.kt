package himanshu.root.taskmanager.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import himanshu.root.taskmanager.R
import himanshu.root.taskmanager.data.local.Task
import himanshu.root.taskmanager.databinding.TaskItemBinding
import java.time.LocalDateTime
import java.time.ZoneOffset

class TaskListAdapter(): ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffUtils()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context))
        return TaskViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val task = getItem(position)
        val date = longToDate(task.updatedAt)
        val dateString = date.toLocalDate().toString() + " | " + date.toLocalTime().toString()
        holder.title.text = task.title
        holder.date.text = dateString
        holder.checked.isChecked = task.isDone

    }

    inner class TaskViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.taskTitle)
        val date = itemView.findViewById<TextView>(R.id.taskDate)
        val checked = itemView.findViewById<CheckBox>(R.id.taskIsDone)

    }


    private fun longToDate(long: Long): LocalDateTime{
        val instance = LocalDateTime.ofEpochSecond(long,0, ZoneOffset.UTC)
        return instance
    }
}