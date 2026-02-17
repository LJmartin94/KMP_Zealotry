package data.example.source.local

import data.RealmDao
import data.RealmDaoImpl
import data.tutorial.Database
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface ExampleDao : RealmDao<ExampleEntityLocal> {

    /**
     * Select all entities from the table.
     *
     * @return all entities.
     */
    suspend fun getAll(): List<ExampleEntityLocal>

    /**
     * Select an entity by id.
     *
     * @param exampleId the entity id.
     * @return the entity with exampleId.
     */
    suspend fun getById(exampleId: ObjectId): ExampleEntityLocal?

    /**
     * Insert or update an example entity in the database. If an example entity already exists, replace it.
     *
     * @param example the example entity to be inserted or updated.
     */
    suspend fun upsert(example: List<ExampleEntityLocal>)

    /**
     * Insert or update example entities in the database. If an example entity already exists, replace it.
     *
     * @param examples the example entities to be inserted or updated.
     */
    suspend fun upsertAll(examples: List<ExampleEntityLocal>)

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

    override suspend fun getAll(): List<ExampleEntityLocal> {
        TODO()
    }

    override suspend fun getById(exampleId: ObjectId): ExampleEntityLocal? {
        TODO()
    }

    override suspend fun upsert(example: List<ExampleEntityLocal>) {
        TODO()
    }

    override suspend fun upsertAll(examples: List<ExampleEntityLocal>) {
        TODO()
    }

    override suspend fun updateToggle(exampleId: ObjectId, toggleStatus: Boolean) {
        TODO()
    }

    override suspend fun deleteToggleTrue(toggleStatus: Boolean): Int {
        TODO()
    }
}
