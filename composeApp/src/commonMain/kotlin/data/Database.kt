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
                // Pass all collection Model classes here.
                val config =
                    RealmConfiguration.Builder(
                        schema = setOf(
                            ExampleEntityLocal::class,
                            ),
                    )
                        .schemaVersion(SCHEMA_VERSION)
                        .compactOnLaunch()
                        .initialDataCallback { mutableRealm ->
                            DatabaseSeeder.seedAll(mutableRealm)
                        }
                        .migration(AutomaticSchemaMigration { context ->
                            DatabaseSeeder.seedFrom(
                                context.oldRealm.schemaVersion(),
                                context.newRealm
                            )
                        })
                        .build()
                realm = Realm.Companion.open(config)
            }
            return realm!!
        } catch (e: Exception) {
            println("Error configuring the realm - instance still null: $e")
            throw e
        }
    }
}