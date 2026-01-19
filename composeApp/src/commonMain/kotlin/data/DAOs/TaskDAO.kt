package data.DAOs

import z.tutorial.RequestState
import z.tutorial.ToDoTask
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface TaskDao {
    fun readActiveTasks(): Flow<RequestState<List<ToDoTask>>>
    fun readCompletedTasks(): Flow<RequestState<List<ToDoTask>>>
    suspend fun addTask(task: ToDoTask)
    suspend fun updateTask(task: ToDoTask)
    suspend fun setCompleted(task: ToDoTask, taskCompleted: Boolean)
    suspend fun setFavourite(task: ToDoTask, isFavourite: Boolean)
    suspend fun deleteTask(task: ToDoTask)
}

class DefaultTaskDAO constructor(
    private val realm: Realm
)  : TaskDao {
    override fun readActiveTasks(): Flow<RequestState<List<ToDoTask>>> {
        return realm?.query<ToDoTask>(query = "completed == $0", false)
            ?.asFlow()
            ?.map { result ->
                RequestState.Success(
                    data = result.list.sortedByDescending { task -> task.favourite },
                )
            } ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    override fun readCompletedTasks(): Flow<RequestState<List<ToDoTask>>> {
        return realm?.query<ToDoTask>(query = "completed == $0", true)
            ?.asFlow()
            ?.map { result ->
                RequestState.Success(data = result.list)
            } ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    override suspend fun addTask(task: ToDoTask) {
        realm?.write { copyToRealm(task) }
    }

    override suspend fun updateTask(task: ToDoTask) {
        realm?.write {
            try {
                val queriedTask =
                    query<ToDoTask>("id == $0", task.id)
                        .first()
                        .find()
                queriedTask?.let {
                    findLatest(it)?.let { currentTask ->
                        currentTask.title = task.title
                        currentTask.description = task.description
                    }
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    override suspend fun setCompleted(
        task: ToDoTask,
        taskCompleted: Boolean,
    ) {
        realm?.write {
            try {
                val queriedTask =
                    query<ToDoTask>("id == $0", task.id)
                        .find()
                        .first()
                queriedTask.apply { completed = taskCompleted }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    override suspend fun setFavourite(
        task: ToDoTask,
        isFavourite: Boolean,
    ) {
        realm?.write {
            try {
                val queriedTask =
                    query<ToDoTask>("id == $0", task.id)
                        .find()
                        .first()
                queriedTask.apply { favourite = isFavourite }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    // TODO: These functions could also return RequestState instead of just printing error
    override suspend fun deleteTask(task: ToDoTask) {
        realm?.write {
            try {
                val queriedTask =
                    query<ToDoTask>("id == $0", task.id)
                        .first()
                        .find()
                queriedTask?.let {
                    findLatest(it)?.let { foundTask ->
                        delete(foundTask)
                    }
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}