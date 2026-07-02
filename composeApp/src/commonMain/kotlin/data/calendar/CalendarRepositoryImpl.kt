package data.calendar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.DateTimeUnit
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

class CalendarRepositoryImpl : CalendarRepository {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _updateFlow = MutableStateFlow(currentCalendarState())
    override val updateFlow: StateFlow<CalendarState> = _updateFlow.asStateFlow()

    init {
        scope.launch {
            while (true) {
                val now: Instant = Clock.System.now()
                val modifiedInstant: Instant = getInstantMinusOffset(moment = now, h = 4)
                val delayBy = getDurationUntilNextDay(moment = modifiedInstant)
                println("getToday - Delaying next check for $delayBy!")
                delay(delayBy.inWholeMilliseconds)
                println("getToday - Coroutine waking up, emitting new day...")
                _updateFlow.value = currentCalendarState()
            }
        }
    }

    override fun forceRefresh() {
        _updateFlow.value = currentCalendarState()
    }

    private fun currentCalendarState(): CalendarState {
        val now: Instant = Clock.System.now()
        val modifiedInstant: Instant = getInstantMinusOffset(moment = now, h = 4)
        val modifiedDay = getDateMinusOffset(moment = now, h = 4)
        return CalendarState(
            dayOfWeek = modifiedDay.dayOfWeek,
            seasonInfo = SeasonInfo(modifiedInstant),
        )
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
