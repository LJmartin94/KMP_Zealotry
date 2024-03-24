package data.timeUtils

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.friday
import zealotry.composeapp.generated.resources.monday
import zealotry.composeapp.generated.resources.saturday
import zealotry.composeapp.generated.resources.sunday
import zealotry.composeapp.generated.resources.thursday
import zealotry.composeapp.generated.resources.tuesday
import zealotry.composeapp.generated.resources.wednesday


/**
 * Gets current day, or previous day if before 4am.
 */
fun getModifiedDay(): DayOfWeek {
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

@Composable
@OptIn(ExperimentalResourceApi::class)
fun DayOfWeek.toCustomString(): String{
    //Assume en locale for now
    return when (this) {
        DayOfWeek.MONDAY -> stringResource(Res.string.monday)
        DayOfWeek.TUESDAY -> stringResource(Res.string.tuesday)
        DayOfWeek.WEDNESDAY -> stringResource(Res.string.wednesday)
        DayOfWeek.THURSDAY -> stringResource(Res.string.thursday)
        DayOfWeek.FRIDAY -> stringResource(Res.string.friday)
        DayOfWeek.SATURDAY -> stringResource(Res.string.saturday)
        DayOfWeek.SUNDAY -> stringResource(Res.string.sunday)
        else -> "Error"
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
