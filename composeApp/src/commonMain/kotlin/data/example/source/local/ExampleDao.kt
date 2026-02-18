package data.example.source.local

import data.RealmDao
import data.RealmDaoImpl
import data.queryEqual
import data.tutorial.Database
import org.mongodb.kbson.ObjectId

interface ExampleDao : RealmDao<ExampleEntityLocal> {

    /**
     * Update the value of a toggle in example with matching id, if found
     *
     * @param exampleId id of the example entity (no-op if entity not found)
     * @param toggleStatus value the toggle should have after updating
     */
    suspend fun updateToggle(exampleId: ObjectId, toggleStatus: Boolean)

    /**
     * Delete all example entities with given toggle value.
     *
     * @param toggleStatus the value of the toggle to be removed
     * @return the number of example entities deleted.
     */
    suspend fun deleteToggleWhen(toggleStatus: Boolean): Unit
}

class ExampleDaoImpl(db: Database) :
    ExampleDao,
    RealmDaoImpl<ExampleEntityLocal> (db, ExampleEntityLocal::class) {

    override suspend fun updateToggle(exampleId: ObjectId, toggleStatus: Boolean) {
        val toUpdate = findById(exampleId)
        if (toUpdate != null){
            update(toUpdate.apply { this.toggle = toggleStatus })
        }
    }

    override suspend fun deleteToggleWhen(toggleStatus: Boolean) {
        val fieldName = ExampleEntityLocal::toggle.name
        val toDelete = findAllByQuery {
            queryEqual(ExampleEntityLocal::class, fieldName, toggleStatus)
        }
        deleteAllFrom(toDelete.toList())
    }
}
