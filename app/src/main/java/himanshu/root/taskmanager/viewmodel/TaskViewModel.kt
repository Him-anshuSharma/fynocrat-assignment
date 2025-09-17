package himanshu.root.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import himanshu.root.taskmanager.data.local.Task
import himanshu.root.taskmanager.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {
    private val query = MutableStateFlow("")

    val tasks: StateFlow<List<Task>> = query.flatMapLatest {
        if(it.isEmpty()){
            repository.getTasks()
        }else{
            repository.searchTasks(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily,emptyList())

    fun setQuery(query:String){
        this.query.value = query
    }

    fun addTask(task: Task){
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    fun deleteTask(task:Task){
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

}