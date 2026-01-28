package data.example.source.local

import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface ExampleDao {
    /**
     * Observes list of example entities.
     *
     * @return all example entities.
     */
    fun observeAll(): Flow<List<ExampleEntityLocal>>

    /**
     * Observes a single entity.
     *
     * @param exampleId the id of the entity.
     * @return the example entity with that exampleId.
     */
    fun observeById(exampleId: ObjectId): Flow<ExampleEntityLocal>

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
     * Delete example entity by id.
     *
     * @return the number of entities deleted. This should always be 1.
     */
    suspend fun deleteById(exampleId: ObjectId): Int

    /**
     * Delete all example entities.
     */
    suspend fun deleteAll()


    /**
     * Delete all example entities with given toggle value.
     *
     * @param toggleStatus the value of the toggle to be removed
     * @return the number of example entities deleted.
     */
    suspend fun deleteToggleTrue(toggleStatus: Boolean): Int
}