package presentation.dayPartMenu

import data.dayPartMenu.DayPart
import toad.ViewEvent
import toad.ViewState

data class DayPartMenuUiState(
    val part: DayPart = DayPart.MORNING,
    var taskButtons: TaskButtonState = TaskButtonState(),
) : ViewState

sealed interface DayPartMenuEvent : ViewEvent
