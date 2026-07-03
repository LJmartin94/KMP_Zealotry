package presentation.dayPartMenu

import data.dayPartMenu.DayPart
import data.dayPartMenu.DayPartMenuRepository
import dev.mokkery.mock
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import toad.ActionScope
import kotlin.test.Test
import kotlin.test.assertEquals

class SetDayPartTest {

    private fun makeDeps() = DayPartMenuActionDependencies(
        dayPartMenuRepository = mock<DayPartMenuRepository>(),
    )

    @Test
    fun `when SetDayPart executes part is updated in state`() = runTest {
        val stateFlow = MutableStateFlow(DayPartMenuUiState())
        val scope = ActionScope<DayPartMenuUiState, DayPartMenuEvent>(stateFlow, Channel(Channel.UNLIMITED))

        SetDayPart(DayPart.EVENING).execute(makeDeps(), scope)

        assertEquals(DayPart.EVENING, stateFlow.value.part)
    }

    @Test
    fun `when SetDayPart executes state reflects each different part`() = runTest {
        val stateFlow = MutableStateFlow(DayPartMenuUiState())
        val scope = ActionScope<DayPartMenuUiState, DayPartMenuEvent>(stateFlow, Channel(Channel.UNLIMITED))

        SetDayPart(DayPart.MORNING).execute(makeDeps(), scope)
        assertEquals(DayPart.MORNING, stateFlow.value.part)

        SetDayPart(DayPart.MIDDAY).execute(makeDeps(), scope)
        assertEquals(DayPart.MIDDAY, stateFlow.value.part)

        SetDayPart(DayPart.EVENING).execute(makeDeps(), scope)
        assertEquals(DayPart.EVENING, stateFlow.value.part)
    }
}
