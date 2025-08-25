package data.tutorial

import domain.tutorial.RequestState
import domain.tutorial.ToDoTask
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.TypedRealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import libs.tristateBool.isTrueOrNull
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

class MongoDB {
    private var realm: Realm? = null

    init {
        configureTheRealm()
    }

    private fun configureTheRealm()  {
        if (realm?.isClosed().isTrueOrNull())
            {
                // Pass all collection Model classes here.
                val config =
                    RealmConfiguration.Builder(
                        schema = setOf(ToDoTask::class),
                    )
                        .compactOnLaunch()
                        .build()
                realm = Realm.open(config)
            }
    }

    fun findItemById(
        key: ObjectId?,
        objType: KClass<*>,
    ): TypedRealmObject? {
        return when (objType) {
            ToDoTask::class -> doQueryForObject<ToDoTask>(key)
            else -> null
        }
    }

    private inline fun <reified T : TypedRealmObject> doQueryForObject(id: ObjectId?): T? {
        return try {
            realm?.query<T>(query = "_id == $0", id)
                ?.find()
                ?.first()
        } catch (e: Exception) {
            null
        }
    }

    fun readActiveTasks(): Flow<RequestState<List<ToDoTask>>> {
        return realm?.query<ToDoTask>(query = "completed == $0", false)
            ?.asFlow()
            ?.map { result ->
                RequestState.Success(
                    data = result.list.sortedByDescending { task -> task.favourite },
                )
            } ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    fun readCompletedTasks(): Flow<RequestState<List<ToDoTask>>> {
        return realm?.query<ToDoTask>(query = "completed == $0", true)
            ?.asFlow()
            ?.map { result ->
                RequestState.Success(data = result.list)
            } ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    suspend fun addTask(task: ToDoTask) {
        realm?.write { copyToRealm(task) }
    }

    suspend fun updateTask(task: ToDoTask)  {
        realm?.write {
            try {
                val queriedTask =
                    query<ToDoTask>("_id == $0", task._id)
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

    suspend fun setCompleted(
        task: ToDoTask,
        taskCompleted: Boolean,
    ) {
        realm?.write {
            try {
                val queriedTask =
                    query<ToDoTask>("_id == $0", task._id)
                        .find()
                        .first()
                queriedTask.apply { completed = taskCompleted }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    suspend fun setFavourite(
        task: ToDoTask,
        isFavourite: Boolean,
    ) {
        realm?.write {
            try {
                val queriedTask =
                    query<ToDoTask>("_id == $0", task._id)
                        .find()
                        .first()
                queriedTask.apply { favourite = isFavourite }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    // TODO: These functions could also return RequestState instead of just printing error
    suspend fun deleteTask(task: ToDoTask) {
        realm?.write {
            try {
                val queriedTask =
                    query<ToDoTask>("_id == $0", task._id)
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
