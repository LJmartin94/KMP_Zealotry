package data

import data.example.Example
import data.example.source.local.ExampleEntityLocal
import io.realm.kotlin.MutableRealm

/**
 * Manages seed data for the Realm database.
 * Called on every app launch — all functions must be idempotent.
 *
 * To add a new seeded row:
 *  - Add a private seed function guarded by alreadySeeded()
 *  - Call it from seedAll()
 *
 * To remove a seeded row (e.g. when its entity is removed from the schema):
 *  - Remove its seed function and its call from seedAll()
 *  - The row itself will be dropped automatically by AutomaticSchemaMigration
 *    when the entity class is removed from the schema set in Database.kt
 */
object DatabaseSeeder {

    fun seedAll(realm: MutableRealm) {
        seedExampleOne(realm)
    }

    private fun seedExampleOne(realm: MutableRealm) {
        if (!realm.alreadySeeded<ExampleEntityLocal>(Example.SEED_EXAMPLE_ONE.value)) {
            realm.copyToRealm(ExampleEntityLocal().apply {
                seedKey = Example.SEED_EXAMPLE_ONE.value
                toggle = true
            })
        }
    }
}
