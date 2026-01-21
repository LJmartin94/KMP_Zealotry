package data.example

data object MockDataBase{
    var id: String = ""
    var toggleState: Boolean = false
}

class ExampleRepositoryImpl(
//    private val networkDataSource: NetworkDataSource,
//    private val localDataSource: ExampleDao,
) : ExampleRepository {
    //PLACEHOLDER CODE FOR WHAT A DB MIGHT ACTUALLY RETURN:
    init {
        println("Initialised Example Repository")
    }

    override suspend fun getExample(): Result<Example> {
        return Result.success(Example(MockDataBase.id, MockDataBase.toggleState))
    }

    override suspend fun updateToggle(toggle: Boolean): Result<Unit> {
        MockDataBase.toggleState = toggle
        println("Toggle state is: ${MockDataBase.toggleState}")
        return Result.success(Unit)
    }
}

//TODO: Complete the data layer implementation for example based on
// https://github.com/android/architecture-samples/tree/main/app/src/main/java/com/example/android/architecture/blueprints/todoapp/data