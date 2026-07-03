package data.calendar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import domain.GetAstronomicalContextUseCase
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant
import kotlinx.datetime.TimeZone

class CalendarRepositoryImpl : CalendarRepository {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _updateFlow = MutableStateFlow(Clock.System.now())
    override val updateFlow: StateFlow<Instant> = _updateFlow.asStateFlow()

    init {
        scope.launch {
            while (true) {
                val now: Instant = Clock.System.now()
                val delayBy = getDurationUntilNextDay(moment = now)
                println("getToday - Delaying next check for $delayBy!")
                delay(delayBy.inWholeMilliseconds)
                println("getToday - Coroutine waking up, emitting new day...")
                _updateFlow.value = Clock.System.now()
            }
        }
    }

    override fun forceRefresh() {
        _updateFlow.value = Clock.System.now()
    }

    private fun getDurationUntilNextDay(
        moment: Instant,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ): Duration = GetAstronomicalContextUseCase.nextAppDayInstant(moment, timeZone) - moment
}
