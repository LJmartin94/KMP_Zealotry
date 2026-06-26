package data

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Generates a RFC-4122 UUID v4 string, compatible with all KMP targets.
 *
 * TODO: Wrap the raw String in a value class (e.g. `value class EntityId(val value: String)`)
 *       with an `init` block that validates the wrapped String is a well-formed UUID,
 *       using [Uuid.parse] or a regex, throwing [IllegalArgumentException] if not.
 */
@OptIn(ExperimentalUuidApi::class)
fun generateEntityId(): String = Uuid.random().toString()
