package presentation.screens.tutorial.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.tutorial.MongoDB
import domain.tutorial.TaskAction
import domain.tutorial.ToDoTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class TaskViewModel(
    private val mongoDB: MongoDB
): ViewModel() {

    fun loadTask(key: ObjectId?): ToDoTask?{
        return mongoDB.findItemById(key, ToDoTask::class) as ToDoTask?
    }

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
        viewModelScope.launch(Dispatchers.IO){
            mongoDB.addTask(task)
        }
    }

    private fun updateTask(task: ToDoTask){
        viewModelScope.launch(Dispatchers.IO){
            mongoDB.updateTask(task)
        }
    }
}