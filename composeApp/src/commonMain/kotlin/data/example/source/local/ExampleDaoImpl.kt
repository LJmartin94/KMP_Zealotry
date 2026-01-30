package data.example.source.local

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.TRUE_PREDICATE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.mongodb.kbson.ObjectId

class ExampleDaoImpl constructor(
    private val realm: Realm
) : ExampleDao {
    /**
     * Observes list of example entities.
     *
     * @return all example entities.
     */
    override fun observeAll(): Flow<List<ExampleEntityLocal>> {
        val query : RealmQuery<ExampleEntityLocal> = realm.query<ExampleEntityLocal>(TRUE_PREDICATE)
        val changeFlow : Flow<ResultsChange<ExampleEntityLocal>> =  query.asFlow()
        val listOfResults : Flow<RealmResults<ExampleEntityLocal>> = changeFlow.map {
            change : ResultsChange<ExampleEntityLocal> -> change.list
        }
        return listOfResults
    }

    /**
     * Observes a single entity.
     *
     * @param exampleId the id of the entity.
     * @return the example entity with that exampleId.
     */
    override fun observeById(exampleId: ObjectId): Flow<ExampleEntityLocal> {
        TODO()
    }

    /**
     * Select all entities from the table.
     *
     * @return all entities.
     */
     override suspend fun getAll(): List<ExampleEntityLocal> {
        TODO()
     }

    /**
     * Select an entity by id.
     *
     * @param exampleId the entity id.
     * @return the entity with exampleId.
     */
     override suspend fun getById(exampleId: ObjectId): ExampleEntityLocal? {
        TODO()
     }

    /**
     * Insert or update an example entity in the database. If an example entity already exists, replace it.
     *
     * @param example the example entity to be inserted or updated.
     */
     override suspend fun upsert(example: List<ExampleEntityLocal>) {
        TODO()
     }

    /**
     * Insert or update example entities in the database. If an example entity already exists, replace it.
     *
     * @param examples the example entities to be inserted or updated.
     */
     override suspend fun upsertAll(examples: List<ExampleEntityLocal>) {
        TODO()
     }

    /**
     * Update the value of a toggle in example
     *
     * @param exampleId id of the example entity
     * @param toggleStatus value the toggle should have after updating
     */
     override suspend fun updateToggle(exampleId: ObjectId, toggleStatus: Boolean) {
        TODO()
     }

    /**
     * Delete example entity by id.
     *
     * @return the number of entities deleted. This should always be 1.
     */
     override suspend fun deleteById(exampleId: ObjectId): Int {
        TODO()
     }

    /**
     * Delete all example entities.
     */
     override suspend fun deleteAll() {
        TODO()
     }


    /**
     * Delete all example entities with given toggle value.
     *
     * @param toggleStatus the value of the toggle to be removed
     * @return the number of example entities deleted.
     */
     override suspend fun deleteToggleTrue(toggleStatus: Boolean): Int {
        TODO()
     }
}