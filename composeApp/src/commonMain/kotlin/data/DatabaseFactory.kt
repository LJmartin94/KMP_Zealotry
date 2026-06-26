package data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

/**
 * Returns the platform-specific absolute path for the app database file.
 * On Android, uses [context] (typed as [Any?] for KMP commonMain compatibility).
 * On iOS, [context] is unused — the document directory is resolved from the platform API.
 */
expect fun databaseFilePath(context: Any?): String

/**
 * Creates the [AppDatabase] instance. Builder configuration lives here in commonMain;
 * only the file path is platform-specific (see [databaseFilePath]).
 *
 * Usage: call once at startup and hold the result as a singleton
 * (provided via Koin's `single { createAppDatabase(context) }`).
 */
fun createAppDatabase(context: Any?): AppDatabase =
    Room.databaseBuilder<AppDatabase>(name = databaseFilePath(context))
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addMigrations(*DatabaseMigration.all.toTypedArray())
        .build()
