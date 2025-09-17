package himanshu.root.taskmanager.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import himanshu.root.taskmanager.R
import himanshu.root.taskmanager.data.local.Task
import himanshu.root.taskmanager.databinding.ActivityMainBinding
import himanshu.root.taskmanager.databinding.AddTaskDialogBinding
import himanshu.root.taskmanager.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewmodel: TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val adapter = TaskListAdapter(
            {task ->
                viewmodel.updateTask(task.copy(isDone = !task.isDone, updatedAt = System.currentTimeMillis()))
            },
            onTap = {id ->
                val intent = Intent(this, TaskDetails::class.java)
                intent.putExtra("index",id)
                startActivity(intent)
            }
        )
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.tasksRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewmodel.tasks.collect {
                Log.d("Hello",it.toString())
                adapter.submitList(it)
            }
        }

        binding.searchBar.addTextChangedListener { it->
            viewmodel.setQuery(it.toString())
        }

        binding.addTaskButton.setOnClickListener {
            showAddTask()
        }
    }

    private fun showAddTask(){
        val binding = AddTaskDialogBinding.inflate(layoutInflater)
        val view = binding.root

        val dialog = MaterialAlertDialogBuilder(this).setTitle("Add Task").setView(view).setPositiveButton("save"){dialog, which ->
            if(binding.taskTitle.text.toString().isEmpty()){
                Toast.makeText(this,"Invalid title", Toast.LENGTH_SHORT).show()
            }
            else{
                viewmodel.addTask(Task(
                    title= binding.taskTitle.text.toString(),
                    description = binding.taskDescription.text.toString(),
                    isDone = false,
                    updatedAt = System.currentTimeMillis())
                )
                dialog.dismiss()
            }
        }
            .setNegativeButton("cancel"){dialog, which ->
                dialog.dismiss()
            }

        dialog.show()

    }
}