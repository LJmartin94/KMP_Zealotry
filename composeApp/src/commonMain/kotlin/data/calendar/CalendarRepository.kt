package data.calendar

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.plus

class CalendarRepository() {
    val updateFlow: Flow<CalendarState> = flow {
        while (true) {
            println("getToday - Emitting day!")
            val now: Instant = Clock.System.now()
            emit(
                CalendarState(
                    dayOfWeek = getZealsday(moment = now),
                    seasonInfo = getSeasonInfo(now),
                )
            )
            val timeToCheck = getZealmorrow(now)
            val delayBy = timeToCheck.minus(now)
            println("getToday - Delaying next check for $delayBy!")
            delay(delayBy.inWholeMilliseconds)
            println("getToday - Coroutine waking up...")
        }
    }

    /**
     * Increment given time until 'tomorrow' as defined by getZealsday()
     */
    private fun getZealmorrow(now: Instant): Instant {
        var future = now
        while (getZealsday(now) == getZealsday(future.plus(1, DateTimeUnit.HOUR))) {
            future = future.plus(1, DateTimeUnit.HOUR)
        }
        while (getZealsday(now) == getZealsday(future.plus(1, DateTimeUnit.MINUTE))) {
            future = future.plus(1, DateTimeUnit.MINUTE)
        }
        while (getZealsday(now) == getZealsday(future)) {
            future = future.plus(1, DateTimeUnit.SECOND)
        }
        return future
    }
}