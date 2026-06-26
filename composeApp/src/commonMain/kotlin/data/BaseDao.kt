package data

import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

/**
 * Interface enforced on all Realm entities in this project.
 * Any RealmObject that does not implement this interface cannot be used
 * with RealmDao or RealmDaoImpl, enforced at compile time via type constraints.
 */
interface DatabaseObject {
    var id: ObjectId //This should be annotated as the @PrimaryKey in the implementing class
    var seedKey: String?
}

// Interface of Generic CRUD operations we expect each specific DAO to be able to fulfill
interface RealmDao<T> where T : RealmObject, T : DatabaseObject {

    // *C* reate ---------------------------------------------------------------------------

    /**
     * Insert or update an entity in the database. If an entity already exists, replace it.
     *
     * NOTE: Functionally the same as update
     *
     * @param entity the entity to be inserted or updated.
     */
    suspend fun insert(entity: T): Result<Unit>

    /**
     * Insert or update entities in the database. If an entity already exists, replace it.
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
    suspend fun findAll(): RealmResults<T>

    /**
     * Select all entities from the table for which query is true.
     *
     * @param query from RealmQueries.kt that defines the criteria that must be satisfied.
     * @return all entities.
     */
    suspend fun findAllByQuery(query: Realm.() -> RealmQuery<T>): RealmResults<T>

    /**
     * Select an entity by id.
     *
     * @param id the entity id.
     * @return the entity with that id.
     */
    suspend fun findById(id: ObjectId): Result<T>

    /**
     * Observes a single entity.
     *
     * @param id the id of the entity.
     * @return flow of the T entity with that id.
     */
    fun observeById(id: ObjectId): Flow<SingleQueryChange<T>>

    /**
     * Observes a single entity by its seed key.
     *
     * @param seedKey the seed key of the entity.
     * @return flow of the T entity with that seed key.
     */
    fun observeBySeedKey(seedKey: String): Flow<SingleQueryChange<T>>

    /**
     * Observes change flow of T entities.
     *
     * TIP: Can get a list of results by taking ret.map { change -> change.list }
     *
     * @return all T entities.
     */
    fun observeAll(): Flow<ResultsChange<T>>

    // *U* pdate ---------------------------------------------------------------------------

    /**
     * Insert or update an entity in the database. If an entity already exists, replace it.
     *
     * NOTE: Functionally the same as insert
     *
     * @param entity the entity to be inserted or updated.
     */
    suspend fun update(entity: T): Result<Unit>

    /**
     * Find an entity by id and apply a mutation within a single write transaction.
     *
     * Prefer this over [findById] + [update] for field-level mutations, as it correctly
     * brings the managed object into the write transaction via [io.realm.kotlin.MutableRealm.findLatest].
     *
     * @param id the id of the entity to update.
     * @param mutation lambda applied to the entity inside the write transaction.
     * @return [Result.success] if the entity was found and updated, [Result.failure] otherwise.
     */
    suspend fun updateById(id: ObjectId, mutation: T.() -> Unit): Result<Unit>

    // *D* elete ---------------------------------------------------------------------------

    /**
     * Delete T entity by id.
     */
    suspend fun deleteById(id: ObjectId): Result<Unit>

    /**
     * Delete T entity.
     */
    suspend fun delete(entity: T): Result<Unit>

    /**
     * Delete all entities in list.
     */
    suspend fun deleteAllFrom(entities: List<T>): Result<Unit>

    /**
     * Delete all entities.
     */
    suspend fun deleteAll(): Result<Unit>
}

// Base DAO class each specific DAO can inherit from to satisfy basic CRUD requirements
open class RealmDaoImpl<T>(
    val db: Database,
    val clazz: KClass<T>
) : RealmDao<T> where T : RealmObject, T : DatabaseObject {
    protected val realm: Realm
        get() {
            return db.getRealm()
        }

    // *C* reate ---------------------------------------------------------------------------

    override suspend fun insert(entity: T): Result<Unit> = runCatching {
        realm.write { copyToRealm(entity) }
    }

    override suspend fun insertAll(entities: List<T>): Result<Unit> = runCatching {
        realm.write { for (entity in entities) { copyToRealm(entity) } }
    }

    // *R* ead -----------------------------------------------------------------------------

    override suspend fun findAll(): RealmResults<T> {
        return realm.query(clazz).find()
    }

    override suspend fun findAllByQuery(query: Realm.() -> RealmQuery<T>): RealmResults<T> {
        return query.invoke(realm).find()
    }

    override suspend fun findById(id: ObjectId): Result<T> = runCatching {
        realm.queryEqual(clazz, "id", id).first().find()
            ?: throw NoSuchElementException("No entity found with id: $id")
    }

    override fun observeById(id: ObjectId): Flow<SingleQueryChange<T>> {
        return realm.queryEqual(clazz, "id", id).first().asFlow()
    }

    override fun observeBySeedKey(seedKey: String): Flow<SingleQueryChange<T>> {
        return realm.queryEqual(clazz, "seedKey", seedKey).first().asFlow()
    }

    override fun observeAll(): Flow<ResultsChange<T>> {
        return realm.query(clazz).asFlow()
    }

    // *U* pdate ---------------------------------------------------------------------------

    override suspend fun update(entity: T): Result<Unit> = runCatching {
        realm.write { copyToRealm(entity) }
    }

    override suspend fun updateById(id: ObjectId, mutation: T.() -> Unit): Result<Unit> {
        return findById(id).mapCatching { entity ->
            realm.write {
                findLatest(entity)?.apply(mutation)
                    ?: throw NoSuchElementException("No entity found with id: $id")
            }
        }
    }

    // *D* elete ---------------------------------------------------------------------------

    override suspend fun deleteById(id: ObjectId): Result<Unit> = runCatching {
        realm.write {
            val entity = queryEqual(clazz, "id", id).first().find()
                ?: throw NoSuchElementException("No entity found with id: $id")
            delete(entity)
        }
    }

    override suspend fun delete(entity: T): Result<Unit> = runCatching {
        realm.write { delete(entity) }
    }

    override suspend fun deleteAll(): Result<Unit> = runCatching {
        realm.write {
            val all = this.query(clazz).find()
            delete(all)
        }
    }

    override suspend fun deleteAllFrom(entities: List<T>): Result<Unit> = runCatching {
        realm.write {
            entities.forEach { entity -> findLatest(entity)?.let { delete(it) } }
        }
    }
}