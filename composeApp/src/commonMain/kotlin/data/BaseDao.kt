package data

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
 * Interface of generic CRUD operations expected of each specific DAO.
 *
 * Feature-specific DAOs extend this interface and are annotated with Room's @Dao.
 * Room generates the concrete implementation; the generic contract here provides
 * compile-time assurance that each DAO covers the full CRUD surface.
 */
interface BaseDao<T : DatabaseObject> {

    // *C* reate ---------------------------------------------------------------------------

    /**
     * Insert or replace an entity in the database.
     *
     * NOTE: Functionally the same as [update]
     *
     * @param entity the entity to be inserted or updated.
     */
    suspend fun insert(entity: T): Result<Unit>

    /**
     * Insert or replace multiple entities in the database.
     *
     * @param entities the entities that will be inserted or updated.
     */
    suspend fun insertAll(entities: List<T>): Result<Unit>

    // *R* ead -----------------------------------------------------------------------------

    /**
     * Select all entities from the table.
     *
     * @return all entities.
     */
    suspend fun findAll(): List<T>

    /**
     * Select an entity by id.
     *
     * @param id the entity id.
     * @return [Result.success] wrapping the entity, or [Result.failure] if not found.
     */
    suspend fun findById(id: String): Result<T>

    /**
     * Observes a single entity by its id.
     *
     * @param id the id of the entity.
     * @return flow emitting the entity, or null if deleted.
     */
    fun observeById(id: String): Flow<T?>

    /**
     * Observes a single entity by its seed key.
     *
     * @param seedKey the seed key of the entity.
     * @return flow emitting the entity, or null if not found.
     */
    fun observeBySeedKey(seedKey: String): Flow<T?>

    /**
     * Observes all T entities as a list.
     *
     * @return flow emitting the full list whenever any row changes.
     */
    fun observeAll(): Flow<List<T>>

    // *U* pdate ---------------------------------------------------------------------------

    /**
     * Insert or replace an entity in the database.
     *
     * NOTE: Functionally the same as [insert]
     *
     * @param entity the entity to be inserted or updated.
     */
    suspend fun update(entity: T): Result<Unit>

    /**
     * Find an entity by id, apply a mutation, and save it within a single transaction.
     *
     * Prefer this over [findById] + [update] for field-level mutations.
     *
     * @param id the id of the entity to update.
     * @param mutation lambda applied to the entity inside the transaction.
     * @return [Result.success] if the entity was found and updated, [Result.failure] otherwise.
     */
    suspend fun updateById(id: String, mutation: T.() -> Unit): Result<Unit>

    // *D* elete ---------------------------------------------------------------------------

    /** Delete T entity by id. */
    suspend fun deleteById(id: String): Result<Unit>

    /** Delete T entity. */
    suspend fun delete(entity: T): Result<Unit>

    /** Delete all entities in list. */
    suspend fun deleteAllFrom(entities: List<T>): Result<Unit>

    /** Delete all entities. */
    suspend fun deleteAll(): Result<Unit>
}
