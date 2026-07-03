package presentation.mainMenu

import app.cash.turbine.test
import data.calendar.CalendarRepository
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import domain.ObserveAstronomicalContextUseCase
import domain.computeAstronomicalContext
import kotlinx.datetime.TimeZone
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import toad.ActionScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class ObserveCalendarContextTest {

    @Test
    fun `when flow emits state is updated with day of week`() = runTest {
        val instant = Clock.System.now()
        val repo = mock<CalendarRepository>()
        every { repo.refresh() } returns Unit
        every { repo.updateFlow } returns flowOf(instant)
        val deps = MainMenuActionDependencies(
            calendarRepository = repo,
            observeAstronomicalContextUseCase = ObserveAstronomicalContextUseCase(repo),
        )
        val stateFlow = MutableStateFlow(MainMenuUiState())
        val scope = ActionScope<MainMenuUiState, MainMenuEvent>(stateFlow, Channel(Channel.UNLIMITED))

        stateFlow.test {
            awaitItem() // initial state
            ObserveCalendarContext.execute(deps, scope)
            val updatedState = awaitItem()
            val expected = computeAstronomicalContext(instant, TimeZone.currentSystemDefault())
            assertEquals(expected.dayOfWeek, updatedState.dayOfWeek)
            assertEquals(expected.season, updatedState.currentSeason)
            assertEquals(expected.dayOfSeason, updatedState.dayOfSeason)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when flow emits multiple times state is updated each time`() = runTest {
        val now = Clock.System.now()
        val instantOne = now
        val instantTwo = now + kotlin.time.Duration.parse("1d")
        val repo = mock<CalendarRepository>()
        every { repo.refresh() } returns Unit
        every { repo.updateFlow } returns flowOf(instantOne, instantTwo)
        val deps = MainMenuActionDependencies(
            calendarRepository = repo,
            observeAstronomicalContextUseCase = ObserveAstronomicalContextUseCase(repo),
        )
        val stateFlow = MutableStateFlow(MainMenuUiState())
        val scope = ActionScope<MainMenuUiState, MainMenuEvent>(stateFlow, Channel(Channel.UNLIMITED))

        stateFlow.test {
            awaitItem() // initial state
            ObserveCalendarContext.execute(deps, scope)
            val firstUpdate = awaitItem()
            assertEquals(computeAstronomicalContext(instantOne, TimeZone.currentSystemDefault()).dayOfWeek, firstUpdate.dayOfWeek)
            val secondUpdate = awaitItem()
            assertEquals(computeAstronomicalContext(instantTwo, TimeZone.currentSystemDefault()).dayOfWeek, secondUpdate.dayOfWeek)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
