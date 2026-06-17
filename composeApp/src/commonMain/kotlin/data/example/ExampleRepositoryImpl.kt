package data.example

import data.example.source.local.ExampleDao
import data.example.source.local.ExampleEntityLocal
import io.realm.kotlin.notifications.DeletedObject
import io.realm.kotlin.notifications.InitialObject
import io.realm.kotlin.notifications.UpdatedObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import org.mongodb.kbson.ObjectId

class ExampleRepositoryImpl(
    private val localDataSource: ExampleDao,
) : ExampleRepository {

    init {
        println("Initialised Example Repository")
    }

    override fun observeCanonicalExample(canonicalKey: String): Flow<Example> {
        return localDataSource.observeBySeedKey(canonicalKey).mapNotNull { change ->
            when (change) {
                is InitialObject -> change.obj.toExternal()
                is UpdatedObject -> change.obj.toExternal()
                is DeletedObject -> {
                    // A canonical entity should never be deleted — this likely indicates a
                    // migration failure or data corruption. TODO: replace with proper error logging.
                    println("ERROR: Canonical example '$canonicalKey' was deleted unexpectedly.")
                    null
                }
                else -> null
            }
        }
    }

    override suspend fun refreshCanonicalExample(canonicalKey: String): Result<Unit> {
        // TODO: fetch from network and write to local DB.
        // Network is a back-up mechanism only - local DB is always the Single Source of Truth.
        // A successful write will automatically trigger observeCanonicalExample to emit.
        return Result.success(Unit)
    }

    override fun observeAllExamples(): Flow<List<Example>> {
        return localDataSource.observeAll().map { change -> change.list.map { it.toExternal() } }
    }

    override suspend fun refreshAllExamples(): Result<Unit> {
        // TODO: fetch all from network and write to local DB.
        // Network is a back-up mechanism only - local DB is always the Single Source of Truth.
        // A successful write will automatically trigger observeAllExamples to emit.
        return Result.success(Unit)
    }

    override fun observeExampleById(id: String): Flow<Example> {
        return localDataSource.observeById(ObjectId(id)).mapNotNull { change ->
            when (change) {
                is InitialObject -> change.obj.toExternal()
                is UpdatedObject -> change.obj.toExternal()
                else -> null
            }
        }
    }

    override suspend fun refreshExampleById(id: String): Result<Unit> {
        // TODO: fetch from network by id and write to local DB.
        // Network is a back-up mechanism only - local DB is always the Single Source of Truth.
        // A successful write will automatically trigger observeExampleById to emit.
        return Result.success(Unit)
    }

    override suspend fun createExample(toggle: Boolean): Result<Example> {
        val entity = ExampleEntityLocal().apply { this.toggle = toggle }
        return localDataSource.insert(entity).map { entity.toExternal() }
    }

    override suspend fun deleteExample(id: String): Result<Unit> {
        return localDataSource.deleteById(ObjectId(id))
    }

    override suspend fun updateToggle(id: String, toggle: Boolean): Result<Unit> {
        return runCatching { localDataSource.updateToggle(ObjectId(id), toggle).getOrThrow() }
    }
}
