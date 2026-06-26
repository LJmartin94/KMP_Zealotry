package data.example.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import data.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO for [ExampleEntityLocal].
 *
 * Room generates a concrete implementation at build time. Generic CRUD operations are
 * delegated to the [BaseDao] contract; feature-specific operations are declared here.
 *
 * Pattern: Room requires @Insert / @Update / @Delete / @Query on abstract methods.
 * Methods that must return [Result] are concrete wrappers that delegate to the internal
 * abstract methods, wrapping any exception with [runCatching].
 */
@Dao
abstract class ExampleDao : BaseDao<ExampleEntityLocal> {

    // Internal Room-generated methods (abstract, annotated) ---------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertInternal(entity: ExampleEntityLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllInternal(entities: List<ExampleEntityLocal>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertIgnoreInternal(entity: ExampleEntityLocal)

    @Update
    abstract suspend fun updateInternal(entity: ExampleEntityLocal)

    @Delete
    abstract suspend fun deleteInternal(entity: ExampleEntityLocal)

    @Delete
    abstract suspend fun deleteAllFromInternal(entities: List<ExampleEntityLocal>)

    @Query("SELECT * FROM example WHERE id = :id LIMIT 1")
    abstract suspend fun findByIdInternal(id: String): ExampleEntityLocal?

    @Query("DELETE FROM example WHERE id = :id")
    abstract suspend fun deleteByIdInternal(id: String)

    @Query("DELETE FROM example")
    abstract suspend fun deleteAllInternal()

    @Query("DELETE FROM example WHERE toggle = :toggleStatus")
    abstract suspend fun deleteToggleWhenInternal(toggleStatus: Boolean)

    // BaseDao interface — concrete Result<Unit> wrappers ------------------------------------

    override suspend fun insert(entity: ExampleEntityLocal): Result<Unit> = runCatching {
        insertInternal(entity)
    }

    override suspend fun insertAll(entities: List<ExampleEntityLocal>): Result<Unit> = runCatching {
        insertAllInternal(entities)
    }

    @Query("SELECT * FROM example")
    abstract override suspend fun findAll(): List<ExampleEntityLocal>

    override suspend fun findById(id: String): Result<ExampleEntityLocal> = runCatching {
        findByIdInternal(id) ?: throw NoSuchElementException("No Example found with id: $id")
    }

    @Query("SELECT * FROM example WHERE id = :id LIMIT 1")
    abstract override fun observeById(id: String): Flow<ExampleEntityLocal?>

    @Query("SELECT * FROM example WHERE seedKey = :seedKey LIMIT 1")
    abstract override fun observeBySeedKey(seedKey: String): Flow<ExampleEntityLocal?>

    @Query("SELECT * FROM example")
    abstract override fun observeAll(): Flow<List<ExampleEntityLocal>>

    override suspend fun update(entity: ExampleEntityLocal): Result<Unit> = runCatching {
        updateInternal(entity)
    }

    @Transaction
    override suspend fun updateById(id: String, mutation: ExampleEntityLocal.() -> Unit): Result<Unit> = runCatching {
        val entity = findByIdInternal(id) ?: throw NoSuchElementException("No Example found with id: $id")
        entity.apply(mutation)
        updateInternal(entity)
    }

    override suspend fun deleteById(id: String): Result<Unit> = runCatching {
        deleteByIdInternal(id)
    }

    override suspend fun delete(entity: ExampleEntityLocal): Result<Unit> = runCatching {
        deleteInternal(entity)
    }

    override suspend fun deleteAllFrom(entities: List<ExampleEntityLocal>): Result<Unit> = runCatching {
        deleteAllFromInternal(entities)
    }

    override suspend fun deleteAll(): Result<Unit> = runCatching {
        deleteAllInternal()
    }

    // ExampleDao-specific operations -------------------------------------------------------

    /**
     * Update the value of a toggle in example with matching id, if found.
     *
     * @param exampleId id of the example entity (no-op if entity not found)
     * @param toggleStatus value the toggle should have after updating
     */
    suspend fun updateToggle(exampleId: String, toggleStatus: Boolean): Result<Unit> {
        return updateById(exampleId) { toggle = toggleStatus }
    }

    /**
     * Delete all example entities with the given toggle value.
     *
     * @param toggleStatus the value of the toggle to be removed
     */
    suspend fun deleteToggleWhen(toggleStatus: Boolean): Result<Unit> = runCatching {
        deleteToggleWhenInternal(toggleStatus)
    }
}

