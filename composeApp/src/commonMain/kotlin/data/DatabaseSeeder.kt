package data

import data.example.Example
import data.example.source.local.ExampleEntityLocal

/**
 * A single seed operation. The [insert] lambda performs an idempotent insert —
 * typically via OnConflictStrategy.IGNORE backed by a unique index on [seedKey].
 */
fun interface SeedEntry {
    suspend fun insert()
}

/**
 * Manages seed data for the Room database.
 * Called on every app launch — all inserts use OnConflictStrategy.IGNORE so existing
 * seeded rows are silently skipped.
 *
 * **To add a new seeded row:** add a [SeedEntry] lambda to [seeds]. No other code needs to change.
 *
 * **To remove a seeded row:** remove its entry from [seeds]. The row is NOT automatically
 * deleted from the database — write a migration if you need to clean up old seeded rows.
 */
object DatabaseSeeder {

    private fun seeds(db: AppDatabase): List<SeedEntry> = listOf(
        SeedEntry { db.exampleDao().insertIgnoreInternal(ExampleEntityLocal(seedKey = Example.FIRST, toggle = true)) },
        SeedEntry { db.exampleDao().insertIgnoreInternal(ExampleEntityLocal(seedKey = Example.SECOND, toggle = false)) },
    )

    suspend fun seedAll(db: AppDatabase) {
        seeds(db).forEach { it.insert() }
    }
}

