package data

import data.example.Example
import data.example.source.local.ExampleEntityLocal
import io.realm.kotlin.MutableRealm

/**
 * Manages seed data for the Realm database.
 *
 * When adding seed data for a new schema version:
 *  1. Bump SCHEMA_VERSION in Database.kt
 *  2. Add an `if (oldVersion < N) seedVN(realm)` block to seedFrom()
 *  3. Implement the private seedVN function with the new seed data.
 *     Note: AutomaticSchemaMigration handles structural schema changes for you.
 *     You only need to handle data concerns here: seeding default rows, transforming
 *     existing values, or migrating data across renamed/retyped fields that Realm cannot infer.
 */
object DatabaseSeeder {

    /** Called on fresh install — treats a fresh db as migrating from version 0. */
    fun seedAll(realm: MutableRealm) = seedFrom(0L, realm)

    /** Called during migration — seeds only what's new since oldVersion. */
    fun seedFrom(oldVersion: Long, realm: MutableRealm) {
        if (oldVersion < 1L) seedV1(realm)
    }

    private fun seedV1(realm: MutableRealm) {
        realm.copyToRealm(ExampleEntityLocal().apply {
            seedKey = Example.SEED_EXAMPLE_ONE
            toggle = true
        })
    }
}
