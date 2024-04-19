package presentation.resourceComposition

import androidx.compose.runtime.Composable
import data.calendar.Season
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.autumn
import zealotry.composeapp.generated.resources.friday
import zealotry.composeapp.generated.resources.monday
import zealotry.composeapp.generated.resources.saturday
import zealotry.composeapp.generated.resources.spring
import zealotry.composeapp.generated.resources.summer
import zealotry.composeapp.generated.resources.sunday
import zealotry.composeapp.generated.resources.thursday
import zealotry.composeapp.generated.resources.tuesday
import zealotry.composeapp.generated.resources.wednesday
import zealotry.composeapp.generated.resources.winter

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