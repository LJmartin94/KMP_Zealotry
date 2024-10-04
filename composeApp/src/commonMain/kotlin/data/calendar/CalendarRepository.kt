package data.calendar

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

class CalendarRepository {
    val updateFlow: Flow<CalendarState> =
        flow {
            while (true) {
                println("getToday - Emitting day!")
                val now: Instant = Clock.System.now()
//            val now: Instant = Instant.parse("2024-04-24T20:53Z")
                val modifiedDay: LocalDateTime = getDateMinusOffset(moment = now)
                val modifiedInstant: Instant = getInstantMinusOffset(moment = now)
                emit(
                    CalendarState(
                        dayOfWeek = modifiedDay.dayOfWeek,
                        seasonInfo = SeasonInfo(modifiedInstant),
                    ),
                )
                val delayBy = getDurationUntilNextDay(moment = modifiedInstant)
                println("getToday - Delaying next check for $delayBy!")
                delay(delayBy.inWholeMilliseconds)
                println("getToday - Coroutine waking up...")
            }
        }

    private fun getDurationUntilNextDay(
        moment: Instant,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ): Duration {
        var nextDay = moment
        val dayOf = { i: Instant -> i.toLocalDateTime(timeZone).dayOfWeek }

        while (dayOf(moment) == dayOf(nextDay.plus(1, DateTimeUnit.HOUR))) {
            nextDay = nextDay.plus(1, DateTimeUnit.HOUR)
        }
        while (dayOf(moment) == dayOf(nextDay.plus(1, DateTimeUnit.MINUTE))) {
            nextDay = nextDay.plus(1, DateTimeUnit.MINUTE)
        }
        while (dayOf(moment) == dayOf(nextDay)) {
            nextDay = nextDay.plus(1, DateTimeUnit.SECOND)
        }
        return nextDay.minus(moment)
    }
}
