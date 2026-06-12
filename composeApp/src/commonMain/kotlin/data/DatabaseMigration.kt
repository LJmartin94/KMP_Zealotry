package data

import io.realm.kotlin.migration.AutomaticSchemaMigration
import org.mongodb.kbson.ObjectId

/**
 * Handles data migrations between schema versions.
 * Called automatically by Realm when the on-disk schema version is older than SCHEMA_VERSION.
 *
 * AutomaticSchemaMigration handles all structural changes (added/removed fields and classes)
 * without any code here. Only add a migration block when you need to transform existing data
 * that Realm cannot infer — e.g. renaming a field, splitting a field, or changing a value's
 * representation.
 *
 * To add a migration from version N to N+1:
 *  - Add an `if (oldVersion < N+1) migrateVNtoVN1(context)` block to migrate()
 *  - Implement the private migration function using context.oldRealm / context.newRealm
 *    Note: these are DynamicRealm / DynamicMutableRealm — fields and classes are addressed
 *    by string name, not by Kotlin types.
 */
object DatabaseMigration {

    val migration = AutomaticSchemaMigration { context -> migrate(context) }

    private fun migrate(context: AutomaticSchemaMigration.MigrationContext) {
        val oldVersion = context.oldRealm.schemaVersion()
        if (oldVersion < 0L) migrateVFaketoV0(context)
    }

    // NOTE: This migration is illustrative only — Realm schema versions cannot be negative,
    // so this block will never execute in practice. It documents the fictional pre-history
    // of ExampleEntityLocal before real migration history began at v0.
    private fun migrateVFaketoV0(context: AutomaticSchemaMigration.MigrationContext) {
        // In vFake, ExampleEntityLocal.toggle was a "Foolean" object with a nested bool: Boolean.
        // In v0, toggle is a plain Boolean.
        // AutomaticSchemaMigration has already created the new toggle: Boolean field (defaulting
        // to false) and dropped the Foolean type, so we must read the old value from oldRealm
        // before writing the transformed result into newRealm.
        context.oldRealm.query("ExampleEntityLocal").find().forEach { oldObj ->
            val id = oldObj.getValue("id", ObjectId::class)
            val oldBool = oldObj.getObject("toggle")?.getValue("bool", Boolean::class) ?: false
            context.newRealm.query("ExampleEntityLocal", "id == $0", id)
                .first().find()
                ?.set("toggle", !oldBool)
        }
    }
}
