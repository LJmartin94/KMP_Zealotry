package data.example

import data.example.source.local.ExampleDao
import data.example.source.local.ExampleEntityLocal
import org.mongodb.kbson.ObjectId

class ExampleRepositoryImpl(
//    private val networkDataSource: NetworkDao,
    private val localDataSource: ExampleDao,
) : ExampleRepository {

    init {
        println("Initialised Example Repository")
    }

    override suspend fun getCanonicalExample(canonicalKey: String, forceUpdate: Boolean): Result<Example> {
        if (forceUpdate) {
            //TODO: fetch example from network first - just a back-up mechanism probably,
            // unless we ever have a data source with more than a Single Source of Truth.
            TODO()
        }
        return localDataSource.findBySeedKey(canonicalKey).map { it.toExternal() }
    }

    override suspend fun getAllExamples(forceUpdate: Boolean): Result<List<Example>> {
        if (forceUpdate) {
            //TODO: fetch examples from network first.
            TODO()
        }
        return runCatching { localDataSource.findAll().map { it.toExternal() } }
    }

    override suspend fun getExampleById(id: String, forceUpdate: Boolean): Result<Example> {
        if (forceUpdate) {
            //TODO: fetch example from network first.
            TODO()
        }
        return runCatching { localDataSource.findById(ObjectId(id)).map { it.toExternal() }.getOrThrow() }
    }

    override suspend fun createExample(toggle: Boolean): Result<Example> {
        return runCatching {
            val entity = ExampleEntityLocal().apply { this.toggle = toggle }
            localDataSource.insert(entity)
            entity.toExternal()
        }
    }

    override suspend fun deleteExample(id: String): Result<Unit> {
        return runCatching { localDataSource.deleteById(ObjectId(id)) }
    }

    override suspend fun updateToggle(id: String, toggle: Boolean): Result<Unit> {
        return runCatching { localDataSource.updateToggle(ObjectId(id), toggle).getOrThrow() }
    }
}
