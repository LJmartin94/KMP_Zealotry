package presentation.screens.tutorial.home

//import kotlinx.coroutines.delay
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.tutorial.MongoDB
import domain.tutorial.RequestState
import domain.tutorial.TaskAction
import domain.tutorial.ToDoTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// I don't understand why we need two identical typealiases.. yet?
typealias MutableTasks = MutableState<RequestState<List<ToDoTask>>>
typealias Tasks = MutableState<RequestState<List<ToDoTask>>>

class HomeViewModel(private val mongoDB: MongoDB): ViewModel() {
    private var _activeTasks: MutableTasks = mutableStateOf(RequestState.Idle)
    val activeTasks: Tasks = _activeTasks

    private var _completedTasks: MutableTasks = mutableStateOf(RequestState.Idle)
    val completedTasks: Tasks = _completedTasks

    init {
        _activeTasks.value = RequestState.Loading
        _completedTasks.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.Main) {
//            delay(500) //artificial delay to see Loading screen.
            mongoDB.readActiveTasks().collectLatest {
                _activeTasks.value = it
            }
        }
        viewModelScope.launch (Dispatchers.Main){
//            delay(500) //artificial delay to see Loading screen.
            mongoDB.readCompletedTasks().collectLatest {
                _completedTasks.value = it
            }
        }
    }

    fun setAction(action: TaskAction){
        when (action) {
            is TaskAction.SetCompleted -> {
                setCompleted(action.task, action.completed)
            }

            is TaskAction.Delete -> {
                deleteTask(action.task)
            }

            is TaskAction.SetFavourite -> {
                setFavourite(action.task, action.isFavourite)
            }

            else -> {}
        }
    }

    private fun setCompleted(task: ToDoTask, completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            mongoDB.setCompleted(task, completed)
        }
    }

    private fun setFavourite(task: ToDoTask, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            mongoDB.setFavourite(task, isFavourite)
        }
    }

    private fun deleteTask(task: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            mongoDB.deleteTask(task)
        }
    }
}