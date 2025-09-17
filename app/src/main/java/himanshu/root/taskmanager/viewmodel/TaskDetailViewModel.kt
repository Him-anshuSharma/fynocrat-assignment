package himanshu.root.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor() : ViewModel() {
    var title: String? = null
    var desc: String? = null
    var status: Boolean = false
}