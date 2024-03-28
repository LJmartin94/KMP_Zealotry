package data.calendar

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime

/**
 * Gets current day, or previous day if before 4am.
 */
fun getTodayWithOffset(): DayOfWeek {
    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    val localTime = now.toLocalDateTime(zone)
    val today = localTime.dayOfWeek.isoDayNumber // Mon: 1 .. Sun: 7
    val yesterday = sanitiseIsoDay(today - 1)
    return when {
        localTime.hour >= 4 -> DayOfWeek(today)
        else -> DayOfWeek(yesterday)
    }
}

/**
 * Map any Int to a value in 1 .. 7, consistent with kotlinx.datetime isoDayNumber.
 * E.g.:
 * -6 == 1 == 8 == Monday
 * -1 == 6 == 13 == Saturday
 *  0 == 7 == 14 == Sunday
 */
private fun sanitiseIsoDay(rawIso: Int): Int {
    var sanitisedIso = rawIso
    if (sanitisedIso > 7) sanitisedIso %= 7
    while (sanitisedIso < 1) sanitisedIso += 7
    return sanitisedIso
}