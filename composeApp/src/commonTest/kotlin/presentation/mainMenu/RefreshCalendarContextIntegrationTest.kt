package presentation.mainMenu

import app.cash.turbine.test
import data.calendar.CalendarRepository
import domain.ObserveAstronomicalContextUseCase
import domain.computeAstronomicalContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import toad.ActionScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class RefreshCalendarContextIntegrationTest {
    private val tz = TimeZone.UTC
    private val initialInstant = Instant.parse("2026-07-17T12:00:00Z")
    private val refreshedInstant = Instant.parse("2026-07-18T12:00:00Z")

    @Test
    fun `onForeground refresh propagates through the pipeline and updates state`() =
        runTest {
            // Fake repo: updateFlow starts at initialInstant; refresh() is a no-op until
            // we configure it — this prevents the scheduling loop's first refresh() from
            // interfering with the assertion setup.
            val updateFlow = MutableStateFlow(initialInstant)
            var onRefresh: () -> Unit = {}
            val repo =
                object : CalendarRepository {
                    override val updateFlow: StateFlow<Instant> = updateFlow.asStateFlow()

                    override fun refresh() = onRefresh()
                }

            val useCase =
                ObserveAstronomicalContextUseCase(
                    calendarRepository = repo,
                    clock =
                        object : Clock {
                            override fun now() = refreshedInstant
                        },
                    timeZone = tz,
                )
            val deps =
                MainMenuActionDependencies(
                    calendarRepository = repo,
                    observeAstronomicalContextUseCase = useCase,
                )
            val stateFlow = MutableStateFlow(MainMenuUiState())
            val scope = ActionScope<MainMenuUiState, MainMenuEvent>(stateFlow, Channel(Channel.UNLIMITED))

            stateFlow.test {
                awaitItem() // discard initial empty state

                // ObserveCalendarContext runs continuously — simulates what the ViewModel does on init
                val observeJob = launch { ObserveCalendarContext.execute(deps, scope) }

                // StateFlow immediately emits initialInstant; state updates to reflect it
                val stateBeforeRefresh = awaitItem()
                val expectedBeforeRefresh = computeAstronomicalContext(initialInstant, tz)
                assertEquals(expectedBeforeRefresh.dayOfWeek, stateBeforeRefresh.dayOfWeek)
                assertEquals(expectedBeforeRefresh.dayOfSeason, stateBeforeRefresh.dayOfSeason)

                // Wire refresh() to emit the next day — simulates Doze drift correction on foreground
                onRefresh = { updateFlow.value = refreshedInstant }

                // RefreshCalendarContext fires; repo emits refreshedInstant; ObserveCalendarContext updates state
                RefreshCalendarContext.execute(deps, scope)

                val stateAfterRefresh = awaitItem()
                val expectedAfterRefresh = computeAstronomicalContext(refreshedInstant, tz)
                assertEquals(expectedAfterRefresh.dayOfWeek, stateAfterRefresh.dayOfWeek)
                assertEquals(expectedAfterRefresh.dayOfSeason, stateAfterRefresh.dayOfSeason)

                observeJob.cancel()
                cancelAndIgnoreRemainingEvents()
            }
        }
}
