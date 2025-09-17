package himanshu.root.taskmanager.data.repository

import himanshu.root.taskmanager.data.local.Task
import himanshu.root.taskmanager.data.local.TaskDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao : TaskDao){
    fun getTasks() = taskDao.getTasks()
    suspend fun addTask(task: Task) = taskDao.addTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    fun searchTasks(query:String) = taskDao.getTasksByKeyword(query)

    suspend fun getTaskById(id:Int) = taskDao.getTaskById(id)

}