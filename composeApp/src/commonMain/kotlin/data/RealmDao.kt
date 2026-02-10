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
    suspend fun insert(entity: T)
    suspend fun insertAll(entities: List<T>)
    suspend fun update(entity: T)
    suspend fun findAll(): RealmResults<T>
    suspend fun findById(id: ObjectId): T?
    suspend fun stream(): Flow<ResultsChange<T>>
    suspend fun delete(entity: T)
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

    // Functionally the same as insert
    override suspend fun update(entity: T) {
        realm.write {
            copyToRealm(entity)
        }
    }

    override suspend fun findAll(): RealmResults<T> {
        return realm.query(clazz).find()
    }

    override suspend fun findById(id: ObjectId): T? {
//        //TODO: check this query syntax actually works
//        return realm.query(clazz, "_id == $0", id).first().find()
        return realm.queryEqual(clazz, "_id", id).first().find()
        //Should field be _id or id? Is the example wrong or is this a quirk of ObjectId? Or PrimaryKey?
    }

    override suspend fun delete(entity: T) {
        realm.write {
            delete(entity)
        }
    }

    override suspend fun stream(): Flow<ResultsChange<T>> {
        return realm.query(clazz).asFlow()
    }

    override suspend fun deleteAll() {
        realm.write {
            val all = this.query(clazz).find()
            delete(all)
        }
    }
}