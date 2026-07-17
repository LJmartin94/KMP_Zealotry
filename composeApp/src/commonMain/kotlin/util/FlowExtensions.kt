package util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold

/**
 * Invokes [action] whenever a [Flow] of nullable values emits null *after* having
 * previously emitted at least one non-null value.
 *
 * This distinguishes two kinds of null:
 * - **Transient null** (null before any value has been seen) — silently ignored.
 * - **Unexpected null** (null after a value existed) — triggers [action].
 *
 * Typical use: detecting that a canonical database entity was unexpectedly deleted,
 * as opposed to simply not yet seeded on first launch.
 *
 * The emitted values are unchanged; [action] is a side effect only.
 */
fun <T : Any> Flow<T?>.onUnexpectedNull(action: () -> Unit): Flow<T?> =
    runningFold(Pair(false, null as T?)) { (seenValue, _), emission ->
        Pair(seenValue || emission != null, emission)
    }.drop(1) // discard the initial runningFold seed state
        .onEach { (seenValue, emission) -> if (emission == null && seenValue) action() }
        .map { (_, emission) -> emission }
