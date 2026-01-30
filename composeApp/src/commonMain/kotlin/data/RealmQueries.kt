package data

import io.realm.kotlin.Realm
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

// https://www.mongodb.com/docs/atlas/device-sdks/realm-query-language/

fun <E : RealmObject, V> Realm.queryEqual(clazz: KClass<E>, fieldName: String, fieldValue: V) : RealmQuery<E> {
        return this.query(clazz, "$fieldName == $0", fieldValue)
}