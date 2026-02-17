package data

import data.tutorial.Database
import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

// Interface of Generic CRUD operations we expect each specific DAO to be able to fulfill
interface RealmDao<T : RealmObject> {

    // *C* reate ---------------------------------------------------------------------------
    suspend fun insert(entity: T)
    suspend fun insertAll(entities: List<T>)

    // *R* ead -----------------------------------------------------------------------------
    suspend fun findAll(): RealmResults<T>
    suspend fun findById(id: ObjectId): T?

    /**
     * Observes a single entity.
     *
     * @param id the id of the entity.
     * @return flow of the T entity with that id.
     */
    fun observeById(id: ObjectId): Flow<T>

    /**
     * Observes change flow of T entities.
     *
     * TIP: Can get a list of results by taking ret.map { change -> change.list }
     *
     * @return all T entities.
     */
    fun observeAll(): Flow<ResultsChange<T>>

    // *U* pdate ---------------------------------------------------------------------------
    suspend fun update(entity: T)

    // *D* elete ---------------------------------------------------------------------------

    /**
     * Delete T entity by id.
     *
     * @return the number of entities deleted. This should always be 1.
     */
    suspend fun deleteById(exampleId: ObjectId): Int

    /**
     * Delete T entity.
     */
    suspend fun delete(entity: T)

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

    override suspend fun findById(id: ObjectId): T? {
//        //TODO: check this query syntax actually works
//        return realm.query(clazz, "_id == $0", id).first().find()
        return realm.queryEqual(clazz, "_id", id).first().find()
        //Should field be _id or id? Is the example wrong or is this a quirk of ObjectId? Or PrimaryKey?
    }

    override fun observeById(id: ObjectId): Flow<T> {
        TODO()
    }

    override fun observeAll(): Flow<ResultsChange<T>> {
        return realm.query(clazz).asFlow()
    }

    // *U* pdate ---------------------------------------------------------------------------

    // Functionally the same as insert
    override suspend fun update(entity: T) {
        realm.write {
            copyToRealm(entity)
        }
    }

    // *D* elete ---------------------------------------------------------------------------

    override suspend fun deleteById(exampleId: ObjectId): Int {
        TODO()
    }

    override suspend fun delete(entity: T) {
        realm.write {
            delete(entity)
        }
    }

    override suspend fun deleteAll() {
        realm.write {
            val all = this.query(clazz).find()
            delete(all)
        }
    }
}