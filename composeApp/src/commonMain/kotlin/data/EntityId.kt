package data

import kotlin.random.Random

/**
 * Generates a RFC-4122 UUID v4 string, compatible with all KMP targets.
 *
 * TODO: After the Kotlin 2.2.0 upgrade:
 *       1. Replace this manual implementation with [kotlin.uuid.Uuid.random().toString()].
 *          See: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.uuid/-uuid/
 *          (Introduced in Kotlin 2.0.0 as @ExperimentalUuidApi)
 *       2. Wrap the raw String in a value class (e.g. `value class EntityId(val value: String)`)
 *          with an `init` block that validates the wrapped String is a well-formed UUID,
 *          using [kotlin.uuid.Uuid.parse] or a regex, throwing [IllegalArgumentException] if not.
 */
fun generateEntityId(): String {
    val bytes = Random.nextBytes(16)
    bytes[6] = ((bytes[6].toInt() and 0x0F) or 0x40).toByte() // version 4
    bytes[8] = ((bytes[8].toInt() and 0x3F) or 0x80).toByte() // variant bits
    return buildString {
        bytes.forEachIndexed { i, b ->
            if (i == 4 || i == 6 || i == 8 || i == 10) append('-')
            append(b.toInt().and(0xFF).toString(16).padStart(2, '0'))
        }
    }
}
