package presentation.mainMenu

import app.cash.turbine.test
import data.calendar.CalendarRepository
import data.calendar.CalendarState
import data.calendar.SeasonInfo
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DayOfWeek
import toad.ActionScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock

class ObserveCalendarContextTest {

    @Test
    fun `when flow emits state is updated with day of week`() = runTest {
        val now = Clock.System.now()
        val calendarState = CalendarState(
            dayOfWeek = DayOfWeek.TUESDAY,
            seasonInfo = SeasonInfo(now),
        )
        val repo = mock<CalendarRepository>()
        every { repo.updateFlow } returns flowOf(calendarState)
        val deps = MainMenuActionDependencies(calendarRepository = repo)
        val stateFlow = MutableStateFlow(MainMenuUiState())
        val scope = ActionScope<MainMenuUiState, MainMenuEvent>(stateFlow, Channel(Channel.UNLIMITED))

        stateFlow.test {
            awaitItem() // initial state

            ObserveCalendarContext.execute(deps, scope)

            val updatedState = awaitItem()
            assertEquals(DayOfWeek.TUESDAY, updatedState.dayOfWeek)
            assertEquals(calendarState.seasonInfo.currentSeason, updatedState.currentSeason)
            assertEquals(calendarState.seasonInfo.dayOfTheSeason, updatedState.dayOfSeason)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when flow emits multiple times state is updated each time`() = runTest {
        val now = Clock.System.now()
        val stateOne = CalendarState(dayOfWeek = DayOfWeek.MONDAY, seasonInfo = SeasonInfo(now))
        val stateTwo = CalendarState(dayOfWeek = DayOfWeek.FRIDAY, seasonInfo = SeasonInfo(now))
        val repo = mock<CalendarRepository>()
        every { repo.updateFlow } returns flowOf(stateOne, stateTwo)
        val deps = MainMenuActionDependencies(calendarRepository = repo)
        val stateFlow = MutableStateFlow(MainMenuUiState())
        val scope = ActionScope<MainMenuUiState, MainMenuEvent>(stateFlow, Channel(Channel.UNLIMITED))

        stateFlow.test {
            awaitItem() // initial state

            ObserveCalendarContext.execute(deps, scope)

            val firstUpdate = awaitItem()
            assertEquals(DayOfWeek.MONDAY, firstUpdate.dayOfWeek)

            val secondUpdate = awaitItem()
            assertEquals(DayOfWeek.FRIDAY, secondUpdate.dayOfWeek)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
