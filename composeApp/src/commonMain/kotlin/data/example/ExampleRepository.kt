package data.example

interface ExampleRepository {

    // --- Seeded examples: retrieved by canonicalKey as defined in Example.companion ---
    suspend fun getCanonicalExample(canonicalKey: String, forceUpdate: Boolean = false): Result<Example>

    // --- User-generated examples ---
    suspend fun getAllExamples(forceUpdate: Boolean = false): Result<List<Example>>
    suspend fun getExampleById(id: String, forceUpdate: Boolean = false): Result<Example>
    suspend fun createExample(toggle: Boolean): Result<Example>
    suspend fun deleteExample(id: String): Result<Unit>

    // --- Works for both seeded and user-generated (caller holds the id from a prior load) ---
    suspend fun updateToggle(id: String, toggle: Boolean): Result<Unit>
}
