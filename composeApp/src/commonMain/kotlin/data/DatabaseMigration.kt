package data

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

/**
 * Handles data migrations between Room schema versions.
 * Room runs the appropriate migration(s) automatically when the on-disk schema version
 * is older than the current [AppDatabase] version.
 *
 * **To add a migration from version N to N+1:**
 *  1. Implement it as a `Migration(N, N+1)` object below, following the existing examples.
 *  2. Add it to [all]. That's it — the builder in [DatabaseFactory] applies them automatically.
 *  3. Bump `version` in the @Database annotation on [AppDatabase].
 *
 * Room's @AutoMigration annotation (declared on @Database) handles simple structural
 * changes (added/removed columns with defaults) without any code here.
 * Add a manual [Migration] only when you need to transform existing data that Room
 * cannot infer — e.g. renaming a column, splitting a field, or changing a value's
 * representation.
 *
 * NOTE: There is currently no migration from v0 because v1 is the first Room schema.
 * The v0 → v1 transition is a fresh install — no data to preserve.
 */
object DatabaseMigration {

    /**
     * All migrations, in ascending version order.
     * The builder applies them sequentially via [addMigrations].
     */
    val all: List<Migration> = listOf(
        // Add new migrations here — no other file needs to change.
        // Example: MIGRATION_1_TO_2,
    )

    // NOTE: This migration is illustrative only — it documents the fictional pre-history
    // of ExampleEntityLocal before real migration history began at v1 (the first Room schema).
    // In vFake, ExampleEntityLocal.toggle was a "Foolean" object with a nested bool: Boolean.
    // In v1, toggle is a plain Boolean.
    //
    // val MIGRATION_VFAKE_TO_V1 = object : Migration(0, 1) {
    //     override fun migrate(db: SQLiteConnection) {
    //         // Step 1: Create a new table with the target schema.
    //         db.execSQL("""
    //             CREATE TABLE example_new (
    //                 id TEXT NOT NULL PRIMARY KEY,
    //                 seedKey TEXT,
    //                 toggle INTEGER NOT NULL DEFAULT 0
    //             )
    //         """.trimIndent())
    //
    //         // Step 2: Copy rows, extracting the nested bool from the old Foolean column.
    //         // (In practice this required a JOIN with a now-dropped foolean table.)
    //         db.execSQL("""
    //             INSERT INTO example_new (id, seedKey, toggle)
    //             SELECT e.id, e.seedKey, COALESCE(f.bool, 0)
    //             FROM example e
    //             LEFT JOIN foolean f ON f.id = e.toggle_id
    //         """.trimIndent())
    //
    //         // Step 3: Swap in the new table.
    //         db.execSQL("DROP TABLE example")
    //         db.execSQL("ALTER TABLE example_new RENAME TO example")
    //     }
    // }
}

