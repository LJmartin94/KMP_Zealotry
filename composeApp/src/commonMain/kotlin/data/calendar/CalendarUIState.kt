package data.calendar

import kotlinx.datetime.DayOfWeek

data class CalendarUIState (
    val isLoading: Boolean = true,
    val today: DayOfWeek = DayOfWeek.MONDAY
)