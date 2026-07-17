package domain

import data.calendar.CalendarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlin.time.Clock

class ObserveAstronomicalContextUseCase(
    private val calendarRepository: CalendarRepository,
    private val clock: Clock = Clock.System,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault(),
) {
    operator fun invoke(): Flow<AstronomicalContext> =
        channelFlow {
            val schedulingJob =
                launch {
                    while (true) {
                        calendarRepository.refresh()
                        val now = clock.now()
                        val next = nextAppDayInstant(now, timeZone)
                        delay((next - now).inWholeMilliseconds)
                    }
                }
            calendarRepository.updateFlow.collect { instant ->
                send(computeAstronomicalContext(instant, timeZone))
            }
            // Reached only when the flow completes (finite flows in tests).
            // In production the StateFlow never completes; cancellation propagates
            // from the ViewModel scope and tears down both coroutines automatically.
            schedulingJob.cancel()
        }
}
