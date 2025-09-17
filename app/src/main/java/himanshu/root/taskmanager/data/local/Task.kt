package himanshu.root.taskmanager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title:String,
    val description:String?,
    val updatedAt:Long,
    val createdAt: Long = System.currentTimeMillis(),
    val isDone: Boolean
)