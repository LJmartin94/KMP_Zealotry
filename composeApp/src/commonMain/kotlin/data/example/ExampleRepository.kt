package data.example

import data.HexStringId
import data.SeedKey

interface ExampleRepository {
    suspend fun getExample(id: HexStringId, forceUpdate: Boolean = false): Result<Example>
    suspend fun getExampleBySeedKey(seedKey: SeedKey): Result<Example>
    suspend fun updateToggle(id: HexStringId, toggle: Boolean): Result<Unit>
}
