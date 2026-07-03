package domain

import app.cash.turbine.test
import data.calendar.CalendarRepository
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class ObserveAstronomicalContextUseCaseTest {

    private val fixedInstant = Instant.parse("2024-07-15T12:00:00Z")
    private val fixedClock = object : Clock { override fun now() = fixedInstant }

    private fun makeUseCase(vararg instants: Instant): ObserveAstronomicalContextUseCase {
        val repo = mock<CalendarRepository>()
        every { repo.refresh() } returns Unit
        every { repo.updateFlow } returns flowOf(*instants)
        return ObserveAstronomicalContextUseCase(
            calendarRepository = repo,
            clock = fixedClock,
            timeZone = TimeZone.UTC,
        )
    }

    @Test
    fun `when repository emits, AstronomicalContext is computed and emitted`() = runTest {
        val useCase = makeUseCase(fixedInstant)
        useCase().test {
            val context = awaitItem()
            assertEquals(Season.SUMMER, context.season)
            assertEquals(computeAstronomicalContext(fixedInstant, TimeZone.UTC).dayOfWeek, context.dayOfWeek)
            awaitComplete()
        }
    }

    @Test
    fun `when repository emits multiple times, contexts are emitted in order`() = runTest {
        val instantOne = Instant.parse("2024-07-15T12:00:00Z")
        val instantTwo = Instant.parse("2024-10-15T12:00:00Z")
        val useCase = makeUseCase(instantOne, instantTwo)
        useCase().test {
            assertEquals(Season.SUMMER, awaitItem().season)
            assertEquals(Season.AUTUMN, awaitItem().season)
            awaitComplete()
        }
    }
}
