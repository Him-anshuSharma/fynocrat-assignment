package himanshu.root.taskmanager.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import himanshu.root.taskmanager.R
import himanshu.root.taskmanager.databinding.ActivityTaskDetailsBinding
import himanshu.root.taskmanager.utils.longToDate
import himanshu.root.taskmanager.viewmodel.TaskDetailsViewModel
import himanshu.root.taskmanager.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskDetails : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()
    private val detailsViewModel: TaskDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("index", -1)
        if (id == -1) finish()

        binding.title.setText(detailsViewModel.title)
        binding.desc.setText(detailsViewModel.desc)
        binding.status.isChecked = detailsViewModel.status
        updateStatusLabel(binding, detailsViewModel.status)

        if (detailsViewModel.title == null && detailsViewModel.desc == null) {
            lifecycleScope.launch {
                val task = taskViewModel.getTaskById(id)
                task?.let { t ->
                    detailsViewModel.title = t.title
                    detailsViewModel.desc = t.description
                    detailsViewModel.status = t.isDone

                    binding.title.setText(t.title)
                    if (!t.description.isNullOrEmpty()) binding.desc.setText(t.description)
                    binding.createdAt.setText("Created At: ${longToDate(t.createdAt)}")
                    binding.updatedAt.setText("Updated At: ${longToDate(t.updatedAt)}")
                    binding.status.isChecked = t.isDone
                    updateStatusLabel(binding, t.isDone)
                }
            }
        }

        binding.title.addTextChangedListener { text ->
            detailsViewModel.title = text?.toString()
        }
        binding.desc.addTextChangedListener { text ->
            detailsViewModel.desc = text?.toString()
        }
        binding.status.setOnCheckedChangeListener { _, isChecked ->
            detailsViewModel.status = isChecked
            updateStatusLabel(binding, isChecked)
        }

        binding.delete.setOnClickListener {
            lifecycleScope.launch {
                val task = taskViewModel.getTaskById(id)
                task?.let {
                    taskViewModel.deleteTask(it)
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
                val oldTask = taskViewModel.getTaskById(id)
                oldTask?.let { t ->
                    val updatedTask = t.copy(
                        title = detailsViewModel.title ?: "",
                        description = detailsViewModel.desc?.trim(),
                        isDone = detailsViewModel.status,
                        updatedAt = System.currentTimeMillis()
                    )
                    taskViewModel.updateTask(updatedTask)
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