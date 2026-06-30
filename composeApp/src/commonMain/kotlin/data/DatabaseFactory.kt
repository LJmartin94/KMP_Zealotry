package data

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

/**
 * Returns a platform-specific [RoomDatabase.Builder] for [AppDatabase].
 * On Android, uses [context] (cast to [android.content.Context]) for the database file path.
 * On iOS, [context] is unused — the document directory is resolved from the platform API.
 */
expect fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<AppDatabase>

/**
 * Creates the [AppDatabase] instance. Builder configuration lives here in commonMain;
 * only the builder creation is platform-specific (see [getDatabaseBuilder]).
 *
 * Usage: call once at startup and hold the result as a singleton
 * (provided via Koin's `single { createAppDatabase(context) }`).
 */
fun createAppDatabase(context: Any?): AppDatabase =
    getDatabaseBuilder(context)
        .setDriver(BundledSQLiteDriver())
        .addMigrations(*DatabaseMigration.all.toTypedArray())
        .build()
