package himanshu.root.taskmanager.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import himanshu.root.taskmanager.data.local.Task
import himanshu.root.taskmanager.databinding.TaskItemBinding
import himanshu.root.taskmanager.utils.longToDate

class TaskListAdapter(val onCheck: (Task) -> Unit, val onTap: (Int) -> Unit): ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffUtils()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater,parent,false)
        return TaskViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val task = getItem(position)
        val date = longToDate(task.updatedAt)

        holder.title.text = task.title
        holder.date.text = date
        holder.checked.isChecked = task.isDone
        holder.checked.setOnClickListener {
            onCheck(task)
        }
        holder.itemView.rootView.setOnClickListener {
            onTap(task.id)
        }
    }

    inner class TaskViewHolder(itemView : TaskItemBinding): RecyclerView.ViewHolder(itemView.root){
        val title = itemView.taskTitle
        val date = itemView.taskDate
        val checked = itemView.taskIsDone

    }



}