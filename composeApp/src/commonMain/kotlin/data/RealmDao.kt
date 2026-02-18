package data

import data.tutorial.Database
import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

// Interface of Generic CRUD operations we expect each specific DAO to be able to fulfill
interface RealmDao<T : RealmObject> {

    // *C* reate ---------------------------------------------------------------------------

    /**
     * Insert or update an entity in the database. If an entity already exists, replace it.
     *
     * NOTE: Functionally the same as update
     *
     * @param entity the entity to be inserted or updated.
     */
    suspend fun insert(entity: T)

    /**
     * Insert or update entities in the database. If an entity already exists, replace it.
     *
     * @param entities the entities that will be inserted or updated.
     */
    suspend fun insertAll(entities: List<T>)

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
    suspend fun findById(id: ObjectId): T?

    /**
     * Observes a single entity.
     *
     * @param id the id of the entity.
     * @return flow of the T entity with that id.
     */
    fun observeById(id: ObjectId): Flow<SingleQueryChange<T>>

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
    suspend fun update(entity: T)

    // *D* elete ---------------------------------------------------------------------------

    /**
     * Delete T entity by id.
     *
     * @return the number of entities deleted. This should always be 1.
     */
    suspend fun deleteById(id: ObjectId): Int

    /**
     * Delete T entity.
     */
    suspend fun delete(entity: T)

    /**
     * Delete all entities in list.
     */
    suspend fun deleteAllFrom(entities: List<T>)

    /**
     * Delete all entities.
     */
    suspend fun deleteAll()
}

// Base DAO class each specific DAO can inherit from to satisfy basic CRUD requirements
open class RealmDaoImpl<T : RealmObject>(
    val db: Database,
    val clazz: KClass<T>
) : RealmDao<T> {
    protected val realm: Realm
        get() {
            return db.getRealm()
        }

    // *C* reate ---------------------------------------------------------------------------

    override suspend fun insert(entity: T) {
        realm.write {
            copyToRealm(entity)
        }
    }

    override suspend fun insertAll(entities: List<T>) {
        realm.write {
            for (entity in entities) {
                copyToRealm(entity)
            }
        }
    }

    // *R* ead -----------------------------------------------------------------------------

    override suspend fun findAll(): RealmResults<T> {
        return realm.query(clazz).find()
    }

    override suspend fun findAllByQuery(query: Realm.() -> RealmQuery<T>): RealmResults<T> {
        return query.invoke(realm).find()
    }

    override suspend fun findById(id: ObjectId): T? {
//        //TODO: check this query syntax actually works
//        return realm.query(clazz, "_id == $0", id).first().find()
        return realm.queryEqual(clazz, "_id", id).first().find()
        //Should field be _id or id? Is the example wrong or is this a quirk of ObjectId? Or PrimaryKey?
        //Change method below if it does actually work.
    }

    override fun observeById(id: ObjectId): Flow<SingleQueryChange<T>> {
        return realm.queryEqual(clazz, "id", id).first().asFlow()
    }

    override fun observeAll(): Flow<ResultsChange<T>> {
        return realm.query(clazz).asFlow()
    }

    // *U* pdate ---------------------------------------------------------------------------

    override suspend fun update(entity: T) {
        realm.write {
            copyToRealm(entity)
        }
    }

    // *D* elete ---------------------------------------------------------------------------

    override suspend fun deleteById(id: ObjectId): Int {
        val toDelete = findById(id)
        if (toDelete != null) {
            delete(toDelete)
            return 1
        }
        println("Couldn't find entity to delete with id: $id")
        return 0
    }

    override suspend fun delete(entity: T) {
        try {
            realm.write {
                delete(entity)
            }
            return
        }
        catch (e: Exception){
            println("Couldn't delete entity: $entity, $e")
        }
    }

    override suspend fun deleteAll() {
        realm.write {
            val all = this.query(clazz).find()
            delete(all)
        }
    }

    override suspend fun deleteAllFrom(entities: List<T>){
        for (entity in entities) {
                delete(entity)
        }
    }
}