package data

import data.example.source.local.ExampleEntityLocal
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.migration.AutomaticSchemaMigration
import z.libs.tristateBool.isTrueOrNull

private const val SCHEMA_VERSION = 1L

class Database {
    private var realm: Realm? = null

    init {
        configureTheRealm()
    }

    /** Expose the configured Realm instance for DAOs / repositories to use. */
    fun getRealm(): Realm {
        return realm ?: configureTheRealm().also { realm = it }
    }

    private fun configureTheRealm() : Realm {
        try {
            if (realm?.isClosed().isTrueOrNull()) {
                // Register all RealmObject entity classes here.
                // Bump SCHEMA_VERSION above whenever you make a structural change:
                //   - Add or remove a class from this set
                //   - Add, remove, rename, or retype a field on any class in this set
                val config =
                    RealmConfiguration.Builder(
                        schema = setOf(
                            ExampleEntityLocal::class,
                            ),
                    )
                        .schemaVersion(SCHEMA_VERSION)
                        .compactOnLaunch()
                        .migration(AutomaticSchemaMigration {
                            // Manual schema migration logic here, e.g. how one field should be transformed into another.
                            // Adding or removing fields handled automatically.
                        })
                        .build()
                realm = Realm.Companion.open(config)
                realm!!.writeBlocking { DatabaseSeeder.seedAll(this) }
            }
            return realm!!
        } catch (e: Exception) {
            println("Error configuring the realm - instance still null: $e")
            throw e
        }
    }
}