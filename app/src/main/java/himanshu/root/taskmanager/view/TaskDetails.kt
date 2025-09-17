package himanshu.root.taskmanager.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import himanshu.root.taskmanager.R
import himanshu.root.taskmanager.databinding.ActivityTaskDetailsBinding
import himanshu.root.taskmanager.utils.longToDate
import himanshu.root.taskmanager.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskDetails : AppCompatActivity() {

    private val viewmodel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("index", -1)
        if (id == -1) finish()

        lifecycleScope.launch {
            val task = viewmodel.getTaskById(id)
            task?.let { t ->
                binding.title.setText(t.title)
                if (!t.description.isNullOrEmpty()) binding.desc.setText(t.description)
                binding.createdAt.setText(longToDate(t.createdAt))
                binding.updatedAt.setText(longToDate(t.updatedAt))
                binding.status.isChecked = t.isDone
                updateStatusLabel(binding, t.isDone)
            }
        }

        binding.status.setOnCheckedChangeListener { _, isChecked ->
            updateStatusLabel(binding, isChecked)
        }

        binding.delete.setOnClickListener {
            lifecycleScope.launch {
                val task = viewmodel.getTaskById(id)
                task?.let {
                    viewmodel.deleteTask(it)
                    finish()
                }
            }
        }

        binding.save.setOnClickListener {
            val titleText = binding.title.text.toString().trim()
            if (titleText.isEmpty()) {
                binding.title.error = "Title cannot be empty"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val oldTask = viewmodel.getTaskById(id)
                oldTask?.let { t ->
                    val updatedTask = t.copy(
                        title = titleText,
                        description = binding.desc.text.toString().trim(),
                        isDone = binding.status.isChecked,
                        updatedAt = System.currentTimeMillis()
                    )
                    viewmodel.updateTask(updatedTask)
                    binding.updatedAt.setText(longToDate(updatedTask.updatedAt))
                    finish()
                }
            }
        }
    }

    private fun updateStatusLabel(binding: ActivityTaskDetailsBinding, isDone: Boolean) {
        if (isDone) {
            binding.statusLabel.text = "Done"
            binding.statusLabel.setTextColor(ContextCompat.getColor(this, R.color.green))
        } else {
            binding.statusLabel.text = "Pending"
            binding.statusLabel.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
    }
}