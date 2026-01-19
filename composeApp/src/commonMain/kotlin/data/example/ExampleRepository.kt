package data.example

interface ExampleRepository {
    suspend fun getExample(taskId: String): Result<Example>
    suspend fun updateToggle(toggle: Boolean): Result<Unit>
}