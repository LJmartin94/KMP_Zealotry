package data

import data.example.source.local.ExampleEntityLocal
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import z.libs.tristateBool.isTrueOrNull

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
                        .compactOnLaunch()
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