package himanshu.root.taskmanager.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import himanshu.root.taskmanager.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.searchBar.addTextChangedListener { it->
        }

        binding.addTaskButton.setOnClickListener {

        }
    }
}