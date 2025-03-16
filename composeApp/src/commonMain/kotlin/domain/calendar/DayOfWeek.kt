package domain.calendar

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

/**
 * Returns the instant minus an offset specified in days, hours, minutes, seconds.
 * Default is minus 4 hours, for my own convenience.
 */
fun getInstantMinusOffset(
    moment: Instant = Clock.System.now(),
    d: Int = 0,
    h: Int = 0,
    m: Int = 0,
    s: Int = 0,
): Instant {
    return moment - Duration.parseIsoString("P${d}DT${h}H${m}M${s}S")
}

fun getDateMinusOffset(
    moment: Instant = Clock.System.now(),
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    d: Int = 0,
    h: Int = 0,
    m: Int = 0,
    s: Int = 0,
): LocalDateTime {
    return getInstantMinusOffset(
        moment = moment,
        d = d,
        h = h,
        m = m,
        s = s,
    ).toLocalDateTime(timeZone)
}
