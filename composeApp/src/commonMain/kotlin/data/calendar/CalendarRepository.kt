package data.calendar

import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    val updateFlow: Flow<CalendarState>
    fun forceRefresh()
}
