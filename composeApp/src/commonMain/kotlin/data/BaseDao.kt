package data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Interface enforced on all Room entities in this project.
 * Any entity that does not implement this interface cannot be used
 * with BaseDao, enforced at compile time via type constraint.
 */
interface DatabaseObject {
    val id: String
    val seedKey: String?
}

/**
 * Generic base DAO for all Room entities in this project.
 *
 * Room resolves [T] when processing each @Dao subclass, allowing @Insert/@Update/@Delete
 * and their [Result] wrappers to be generated once here rather than repeated per entity.
 *
 * @Query operations require hardcoded table names and cannot be generic — subclasses declare
 * them as abstract overrides and annotate them with @Query. The abstract stubs declared here
 * allow the concrete wrapper implementations ([findById], [updateById], [deleteById],
 * [deleteAll]) to live here rather than being repeated in every entity DAO.
 */
abstract class BaseDao<T : DatabaseObject> {

    // *C* reate ---------------------------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertInternal(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertAllInternal(entities: List<T>)

    /** Idempotent insert for seeding — silently skips rows that already exist. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertIgnoreInternal(entity: T)

    /**
     * Insert or replace an entity in the database.
     * NOTE: Functionally the same as [update].
     */
    suspend fun insert(entity: T): Result<Unit> = runCatching { insertInternal(entity) }

    /**
     * Insert or replace multiple entities in the database.
     */
    suspend fun insertAll(entities: List<T>): Result<Unit> = runCatching { insertAllInternal(entities) }

    // *R* ead -----------------------------------------------------------------------------

    /** Select all entities from the table. */
    abstract suspend fun findAll(): List<T>

    /** Subclasses annotate the override with @Query. Used by [findById] and [updateById]. */
    protected abstract suspend fun findByIdInternal(id: String): T?

    /**
     * Select an entity by id.
     *
     * @return [Result.success] wrapping the entity, or [Result.failure] if not found.
     */
    suspend fun findById(id: String): Result<T> = runCatching {
        findByIdInternal(id) ?: throw NoSuchElementException("No entity found with id: $id")
    }

    /**
     * Observes a single entity by its id.
     *
     * @return flow emitting the entity, or null if deleted.
     */
    abstract fun observeById(id: String): Flow<T?>

    /**
     * Observes a single entity by its seed key.
     *
     * @return flow emitting the entity, or null if not found.
     */
    abstract fun observeBySeedKey(seedKey: String): Flow<T?>

    /**
     * Observes all entities as a list.
     *
     * @return flow emitting the full list whenever any row changes.
     */
    abstract fun observeAll(): Flow<List<T>>

    // *U* pdate ---------------------------------------------------------------------------

    @Update
    protected abstract suspend fun updateInternal(entity: T)

    /**
     * Insert or replace an entity in the database.
     * NOTE: Functionally the same as [insert].
     */
    suspend fun update(entity: T): Result<Unit> = runCatching { updateInternal(entity) }

    /**
     * Find an entity by id, apply a mutation, and save it within a single transaction.
     *
     * Prefer this over [findById] + [update] for field-level mutations.
     *
     * @return [Result.success] if the entity was found and updated, [Result.failure] otherwise.
     */
    @Transaction
    open suspend fun updateById(id: String, mutation: T.() -> Unit): Result<Unit> = runCatching {
        val entity = findByIdInternal(id)
            ?: throw NoSuchElementException("No entity found with id: $id")
        entity.apply(mutation)
        updateInternal(entity)
    }

    // *D* elete ---------------------------------------------------------------------------

    @Delete
    protected abstract suspend fun deleteInternal(entity: T)

    @Delete
    protected abstract suspend fun deleteAllFromInternal(entities: List<T>)

    /** Subclasses annotate the override with @Query. Used by [deleteById]. */
    protected abstract suspend fun deleteByIdInternal(id: String)

    /** Subclasses annotate the override with @Query. Used by [deleteAll]. */
    protected abstract suspend fun deleteAllInternal()

    /** Delete an entity. */
    suspend fun delete(entity: T): Result<Unit> = runCatching { deleteInternal(entity) }

    /** Delete all entities in list. */
    suspend fun deleteAllFrom(entities: List<T>): Result<Unit> = runCatching { deleteAllFromInternal(entities) }

    /** Delete entity by id. */
    suspend fun deleteById(id: String): Result<Unit> = runCatching { deleteByIdInternal(id) }

    /** Delete all entities. */
    suspend fun deleteAll(): Result<Unit> = runCatching { deleteAllInternal() }
}
