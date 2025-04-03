package presentation.screens.tutorial.task

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.tutorial.MongoDB
import domain.tutorial.TaskAction
import domain.tutorial.ToDoTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class TaskViewModel(
    private val mongoDB: MongoDB
): ScreenModel {

    fun setAction(action: TaskAction){
        when (action) {
            is TaskAction.Add -> {
                addTask(action.task)
            }

            is TaskAction.Update -> {
                updateTask(action.task)
            }

            else -> {}
        }
    }

    private fun addTask(task: ToDoTask){
        screenModelScope.launch(Dispatchers.IO){
            mongoDB.addTask(task)
        }
    }

    private fun updateTask(task: ToDoTask){
        screenModelScope.launch(Dispatchers.IO){
            mongoDB.updateTask(task)
        }
    }
}