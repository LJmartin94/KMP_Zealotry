package z.calendar

import kotlin.time.Clock
import kotlinx.datetime.DayOfWeek

data class CalendarState(
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val seasonInfo: SeasonInfo = SeasonInfo(Clock.System.now()),
)
