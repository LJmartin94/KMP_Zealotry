package data.example

interface ExampleRepository {
    suspend fun getTask(taskId: String)
}