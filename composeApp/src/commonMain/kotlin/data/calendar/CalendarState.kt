package data.calendar

import kotlinx.datetime.DayOfWeek

data class CalendarState (
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val seasonInfo: SeasonInfo = SeasonInfo(),
)