package data.example

interface ExampleRepository {
    suspend fun getExample(taskId: String): Result<Example>
    suspend fun updateExample(toggle: Boolean): Result<Unit>
}