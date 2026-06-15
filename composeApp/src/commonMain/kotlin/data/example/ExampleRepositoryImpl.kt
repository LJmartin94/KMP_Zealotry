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
    //PLACEHOLDER CODE FOR WHAT A DB MIGHT ACTUALLY RETURN:
    init {
        println("Initialised Example Repository")
    }

    override suspend fun getExampleBySeedKey(seedKey: SeedKey): Result<Example> {
        return localDataSource.findBySeedKey(seedKey)
            .map { entity -> Example(entity.id.toHexString(), entity.toggle) }
    }

    override suspend fun getExample(id: HexStringId, forceUpdate: Boolean): Result<Example> {
        if (forceUpdate) {
            //TODO: fetch example from network first - just a back-up mechanism probably,
            // unless we ever have a data source with more than a Single Source of Truth.
            TODO()
        }
        return localDataSource.findById(id.obj())
            .map { entity -> Example(entity.id.toHexString(), entity.toggle) }
        //return Result.success(Example(MockDataBase.id, MockDataBase.toggleState))
    }

    override suspend fun updateToggle(id: HexStringId, toggle: Boolean): Result<Unit> {
        MockDataBase.toggleState = toggle
        println("Toggle state is: ${MockDataBase.toggleState}")
        localDataSource.updateToggle(id.obj(), toggle)
        return Result.success(Unit)
    }
}
