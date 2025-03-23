package presentation.screens.tutorial.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.tutorial.MongoDB
import domain.tutorial.RequestState
import domain.tutorial.ToDoTask
import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// I don't understand why we need two identical typealiases.. yet?
typealias MutableTasks = MutableState<RequestState<List<ToDoTask>>>
typealias Tasks = MutableState<RequestState<List<ToDoTask>>>

class HomeViewModel(private val mongoDB: MongoDB): ScreenModel {
    private var _activeTasks: MutableTasks = mutableStateOf(RequestState.Idle)
    val activeTasks: Tasks = _activeTasks

    private var _completedTasks: MutableTasks = mutableStateOf(RequestState.Idle)
    val completedTasks: Tasks = _completedTasks

    init {
        _activeTasks.value = RequestState.Loading
        _completedTasks.value = RequestState.Loading
        screenModelScope.launch(Dispatchers.Main) {
//            delay(500) //artificial delay to see Loading screen.
            mongoDB.readActiveTasks().collectLatest {
                _activeTasks.value = it
            }
        }
        screenModelScope.launch (Dispatchers.Main){
//            delay(500) //artificial delay to see Loading screen.
            mongoDB.readCompletedTasks().collectLatest {
                _completedTasks.value = it
            }
        }
    }
}