package data.calendar

import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

interface CalendarRepository {
    val updateFlow: Flow<Instant>
    fun refresh()
}
