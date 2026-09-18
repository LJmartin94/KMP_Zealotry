package util

import co.touchlab.kermit.Logger as Kermit

/**
 * Thin facade over [Kermit], pinning every level to one call shape —
 * `Logger.d(tag) { "message" }` — regardless of Kermit's own (throwable-first) signature.
 *
 * Backed by Kermit's default platform log writers (Logcat on Android, os_log/NSLog on iOS,
 * stdout on desktop), so no additional wiring is needed at app startup.
 */
object Logger {
    fun v(
        tag: String,
        throwable: Throwable? = null,
        message: () -> String,
    ) = Kermit.v(throwable = throwable, tag = tag, message = message)

    fun d(
        tag: String,
        throwable: Throwable? = null,
        message: () -> String,
    ) = Kermit.d(throwable = throwable, tag = tag, message = message)

    fun i(
        tag: String,
        throwable: Throwable? = null,
        message: () -> String,
    ) = Kermit.i(throwable = throwable, tag = tag, message = message)

    fun w(
        tag: String,
        throwable: Throwable? = null,
        message: () -> String,
    ) = Kermit.w(throwable = throwable, tag = tag, message = message)

    fun e(
        tag: String,
        throwable: Throwable? = null,
        message: () -> String,
    ) = Kermit.e(throwable = throwable, tag = tag, message = message)

    /** "What a terrible failure" — the highest severity level (Kermit's [co.touchlab.kermit.Severity.Assert]). */
    fun wtf(
        tag: String,
        throwable: Throwable? = null,
        message: () -> String,
    ) = Kermit.a(throwable = throwable, tag = tag, message = message)
}
