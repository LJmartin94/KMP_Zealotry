package data.example

import kotlinx.coroutines.flow.Flow

interface ExampleRepository {
    // --- Seeded examples: retrieved by CanonicalKey as defined in Example.CanonicalKey ---
    fun observeCanonicalExample(canonicalKey: Example.CanonicalKey): Flow<Example>

    suspend fun refreshCanonicalExample(canonicalKey: Example.CanonicalKey): Result<Unit>

    // --- User-generated examples ---
    fun observeAllExamples(): Flow<List<Example>>

    suspend fun refreshAllExamples(): Result<Unit>

    fun observeExampleById(id: String): Flow<Example>

    suspend fun refreshExampleById(id: String): Result<Unit>

    suspend fun createExample(toggle: Boolean): Result<Example>

    suspend fun deleteExample(id: String): Result<Unit>

    // --- Works for both seeded and user-generated (caller holds the id from a prior load) ---
    suspend fun updateToggle(
        id: String,
        toggle: Boolean,
    ): Result<Unit>
}
