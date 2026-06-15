package data

import data.example.Example
import data.example.source.local.ExampleEntityLocal
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

/**
 * Describes a single row to be pre-seeded into the database.
 *
 * @param clazz the Realm entity class to query against when checking idempotency.
 * @param seedKey the unique key used to identify this seeded row.
 * @param create factory lambda that produces the unmanaged entity to insert.
 */
data class SeedEntry<T : RealmObject>(
    val clazz: KClass<T>,
    val seedKey: String,
    val create: () -> T,
)

/**
 * Manages seed data for the Realm database.
 * Called on every app launch — all seed operations are idempotent.
 *
 * To add a new seeded row: add a [SeedEntry] to [seeds].
 * To remove a seeded row: remove its [SeedEntry] from [seeds].
 *   The row itself will be dropped automatically by AutomaticSchemaMigration
 *   when the entity class is removed from the schema set in Database.kt
 */
object DatabaseSeeder {

    private val seeds = listOf(
        SeedEntry(
            clazz = ExampleEntityLocal::class,
            seedKey = Example.FIRST,
            create = { ExampleEntityLocal().apply { seedKey = Example.FIRST; toggle = true } }
        ),
        SeedEntry(
            clazz = ExampleEntityLocal::class,
            seedKey = Example.SECOND,
            create = { ExampleEntityLocal().apply { seedKey = Example.SECOND; toggle = false } }
        ),
    )

    fun seedAll(realm: MutableRealm) {
        seeds.forEach { entry -> realm.seedIfNeeded(entry) }
    }

    private fun <T : RealmObject> MutableRealm.seedIfNeeded(entry: SeedEntry<T>) {
        if (queryEqual(entry.clazz, "seedKey", entry.seedKey).first().find() == null) {
            copyToRealm(entry.create())
        }
    }
}
