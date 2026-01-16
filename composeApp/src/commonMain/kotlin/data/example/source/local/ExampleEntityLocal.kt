package data.example.source.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

/**
 * Data layer representation of the Example entity (how Example is stored in Realm), stored locally.
 */
class ExampleEntityLocal: RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var toggle: Boolean = false
}