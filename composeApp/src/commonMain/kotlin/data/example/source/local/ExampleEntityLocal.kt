package data.example.source.local

import data.DatabaseObject
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

/**
 * Data layer representation of the Example entity (how Example is stored in Realm), stored locally.
 */
class ExampleEntityLocal : RealmObject, DatabaseObject {
    @PrimaryKey
    override var id: ObjectId = ObjectId()
    override var seedKey: String? = null
    var toggle: Boolean = false
}