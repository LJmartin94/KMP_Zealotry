package data

import io.realm.kotlin.TypedRealm
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

// https://www.mongodb.com/docs/atlas/device-sdks/realm-query-language/

fun <E : RealmObject, V> TypedRealm.queryEqual(clazz: KClass<E>, fieldName: String, fieldValue: V) : RealmQuery<E> {
        return this.query(clazz, "$fieldName == $0", fieldValue)
}

inline fun <reified E : RealmObject> TypedRealm.alreadySeeded(seedKey: String): Boolean {
    return queryEqual(E::class, "seedKey", seedKey).first().find() != null
}
