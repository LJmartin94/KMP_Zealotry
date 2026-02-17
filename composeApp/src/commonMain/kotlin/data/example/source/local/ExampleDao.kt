package data.example.source.local

import data.RealmDao
import data.RealmDaoImpl
import data.tutorial.Database
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface ExampleDao : RealmDao<ExampleEntityLocal> {

    /**
     * Update the value of a toggle in example
     *
     * @param exampleId id of the example entity
     * @param toggleStatus value the toggle should have after updating
     */
    suspend fun updateToggle(exampleId: ObjectId, toggleStatus: Boolean)

    /**
     * Delete all example entities with given toggle value.
     *
     * @param toggleStatus the value of the toggle to be removed
     * @return the number of example entities deleted.
     */
    suspend fun deleteToggleTrue(toggleStatus: Boolean): Int
}

class ExampleDaoImpl(db: Database) :
    ExampleDao,
    RealmDaoImpl<ExampleEntityLocal> (db, ExampleEntityLocal::class) {

    override suspend fun updateToggle(exampleId: ObjectId, toggleStatus: Boolean) {
        TODO()
    }

    override suspend fun deleteToggleTrue(toggleStatus: Boolean): Int {
        TODO()
    }
}
