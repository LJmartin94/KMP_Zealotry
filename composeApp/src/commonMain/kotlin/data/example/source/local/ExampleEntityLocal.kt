package data.example.source.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import data.DatabaseObject
import data.generateEntityId

/**
 * Data layer representation of the Example entity (how Example is stored locally in Room).
 *
 * The [seedKey] column has a unique index — this enforces that at most one row can
 * exist per seed key, which makes seeding idempotent via OnConflictStrategy.IGNORE.
 * Non-canonical rows (user-created) have seedKey = null; SQLite treats each NULL as
 * distinct, so multiple null values satisfy the unique constraint correctly.
 */
@Entity(
    tableName = "example",
    indices = [Index(value = ["seedKey"], unique = true)],
)
data class ExampleEntityLocal(
    @PrimaryKey
    override var id: String = generateEntityId(),
    override var seedKey: String? = null,
    var toggle: Boolean = false,
) : DatabaseObject
