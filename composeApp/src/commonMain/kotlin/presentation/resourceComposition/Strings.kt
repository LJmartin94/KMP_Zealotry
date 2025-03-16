package presentation.resourceComposition

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import domain.calendar.FestiveDay
import domain.calendar.Season
import kotlinx.datetime.DayOfWeek
import libs.localisation.getLocale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.autumn
import zealotry.composeapp.generated.resources.autumn_start
import zealotry.composeapp.generated.resources.friday
import zealotry.composeapp.generated.resources.mid_autumn
import zealotry.composeapp.generated.resources.mid_spring
import zealotry.composeapp.generated.resources.mid_summer
import zealotry.composeapp.generated.resources.mid_winter
import zealotry.composeapp.generated.resources.monday
import zealotry.composeapp.generated.resources.ordinal
import zealotry.composeapp.generated.resources.ordinal_number
import zealotry.composeapp.generated.resources.saturday
import zealotry.composeapp.generated.resources.spring
import zealotry.composeapp.generated.resources.spring_start
import zealotry.composeapp.generated.resources.summer
import zealotry.composeapp.generated.resources.summer_start
import zealotry.composeapp.generated.resources.sunday
import zealotry.composeapp.generated.resources.thursday
import zealotry.composeapp.generated.resources.tuesday
import zealotry.composeapp.generated.resources.wednesday
import zealotry.composeapp.generated.resources.winter
import zealotry.composeapp.generated.resources.winter_start

@Composable
@OptIn(ExperimentalResourceApi::class)
fun DayOfWeek.toResourceString(): String {
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

@Composable
@OptIn(ExperimentalResourceApi::class)
fun Season.toResourceString(): String {
    return when (this) {
        Season.SPRING -> stringResource(Res.string.spring)
        Season.SUMMER -> stringResource(Res.string.summer)
        Season.AUTUMN -> stringResource(Res.string.autumn)
        Season.WINTER -> stringResource(Res.string.winter)
    }
}

@Composable
@OptIn(ExperimentalResourceApi::class)
fun FestiveDay.toResourceString(): String {
    return when (this) {
        FestiveDay.SPRING_START -> stringResource(Res.string.spring_start)
        FestiveDay.MID_SPRING -> stringResource(Res.string.mid_spring)
        FestiveDay.SUMMER_START -> stringResource(Res.string.summer_start)
        FestiveDay.MID_SUMMER -> stringResource(Res.string.mid_summer)
        FestiveDay.AUTUMN_START -> stringResource(Res.string.autumn_start)
        FestiveDay.MID_AUTUMN -> stringResource(Res.string.mid_autumn)
        FestiveDay.WINTER_START -> stringResource(Res.string.winter_start)
        FestiveDay.MID_WINTER -> stringResource(Res.string.mid_winter)
    }
}

@Composable
@OptIn(ExperimentalResourceApi::class)
fun Int.toOrdinalNumberString(locale: String = Locale.current.language): String {
    val pluralForm = getLocale(locale).getOrdinal(this)
    val ordinalSignifier = stringArrayResource(Res.string.ordinal)[pluralForm.ordinal]
    return stringResource(Res.string.ordinal_number, this, ordinalSignifier)
}
