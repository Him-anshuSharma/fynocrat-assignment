package himanshu.root.taskmanager.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import himanshu.root.taskmanager.R
import himanshu.root.taskmanager.data.local.Task
import himanshu.root.taskmanager.databinding.ActivityMainBinding
import himanshu.root.taskmanager.databinding.AddTaskDialogBinding
import himanshu.root.taskmanager.viewmodel.TaskViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewmodel: TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.searchBar.addTextChangedListener { it->
        }

        binding.addTaskButton.setOnClickListener {
            showAddTask()
        }
    }

    private fun showAddTask(){
        val binding = AddTaskDialogBinding.inflate(layoutInflater)
        val view = binding.root

        val dialog = MaterialAlertDialogBuilder(this).setTitle("Add Task").setView(view).setPositiveButton("save"){dialog, which ->
            if(binding.taskTitle.toString().isEmpty()){
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