package data.example

import data.HexStringId
import data.SeedKey
import data.example.source.local.ExampleDao
import org.mongodb.kbson.ObjectId

data object MockDataBase{
    var id: ObjectId = ObjectId()
    var toggleState: Boolean = false
}

class ExampleRepositoryImpl(
//    private val networkDataSource: NetworkDao,
    private val localDataSource: ExampleDao,
) : ExampleRepository {
    init {
        println("Initialised Example Repository")
    }

    override suspend fun getExampleBySeedKey(seedKey: SeedKey): Result<Example> {
        return localDataSource.findBySeedKey(seedKey).map { it.toExternal() }
    }

    override suspend fun getExample(id: HexStringId, forceUpdate: Boolean): Result<Example> {
        if (forceUpdate) {
            //TODO: fetch example from network first - just a back-up mechanism probably,
            // unless we ever have a data source with more than a Single Source of Truth.
            TODO()
        }
        return localDataSource.findById(id.obj()).map { it.toExternal() }
    }

    override suspend fun updateToggle(id: HexStringId, toggle: Boolean): Result<Unit> {
        MockDataBase.toggleState = toggle
        println("Toggle state is: ${MockDataBase.toggleState}")
        //TODO: Need to clean up MockDataBase completely, and fix the button as it's not working in this halfway house.
        return runCatching { localDataSource.updateToggle(id.obj(), toggle).getOrThrow() }
    }
}
