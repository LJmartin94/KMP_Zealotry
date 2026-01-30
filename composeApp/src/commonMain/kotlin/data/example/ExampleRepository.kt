package data.example

interface ExampleRepository {
    suspend fun getExample(): Result<Example>
    suspend fun updateToggle(toggle: Boolean): Result<Unit>
}
