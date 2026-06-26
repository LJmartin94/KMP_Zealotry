package data.example.source.local

import androidx.room.Dao
import androidx.room.Query
import data.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO for [ExampleEntityLocal].
 *
 * Generic CRUD is inherited from [BaseDao]. Only entity-specific @Query overrides
 * and example-specific operations are declared here.
 */
@Dao
abstract class ExampleDao : BaseDao<ExampleEntityLocal>() {

    // *R* ead — @Query overrides (table name is entity-specific) --------------------------

    @Query("SELECT * FROM example")
    abstract override suspend fun findAll(): List<ExampleEntityLocal>

    @Query("SELECT * FROM example WHERE id = :id LIMIT 1")
    protected abstract override suspend fun findByIdInternal(id: String): ExampleEntityLocal?

    @Query("SELECT * FROM example WHERE id = :id LIMIT 1")
    abstract override fun observeById(id: String): Flow<ExampleEntityLocal?>

    @Query("SELECT * FROM example WHERE seedKey = :seedKey LIMIT 1")
    abstract override fun observeBySeedKey(seedKey: String): Flow<ExampleEntityLocal?>

    @Query("SELECT * FROM example")
    abstract override fun observeAll(): Flow<List<ExampleEntityLocal>>

    // *D* elete — @Query overrides --------------------------------------------------------

    @Query("DELETE FROM example WHERE id = :id")
    protected abstract override suspend fun deleteByIdInternal(id: String)

    @Query("DELETE FROM example")
    protected abstract override suspend fun deleteAllInternal()

    // Example-specific operations ---------------------------------------------------------

    /**
     * Update the value of a toggle in example with matching id, if found.
     *
     * @param exampleId id of the example entity (no-op if entity not found)
     * @param toggleStatus value the toggle should have after updating
     */
    suspend fun updateToggle(exampleId: String, toggleStatus: Boolean): Result<Unit> =
        updateById(exampleId) { toggle = toggleStatus }

    /**
     * Delete all example entities with the given toggle value.
     *
     * @param toggleStatus the value of the toggle to be removed
     */
    suspend fun deleteToggleWhen(toggleStatus: Boolean): Result<Unit> = runCatching {
        deleteToggleWhenInternal(toggleStatus)
    }

    @Query("DELETE FROM example WHERE toggle = :toggleStatus")
    protected abstract suspend fun deleteToggleWhenInternal(toggleStatus: Boolean)
}

