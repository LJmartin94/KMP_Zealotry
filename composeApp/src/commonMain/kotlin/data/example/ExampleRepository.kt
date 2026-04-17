package data.example

import org.mongodb.kbson.ObjectId

interface ExampleRepository {
    suspend fun getExample(id: ObjectId, forceUpdate: Boolean = false): Result<Example>
    suspend fun updateToggle(toggle: Boolean): Result<Unit>
}
