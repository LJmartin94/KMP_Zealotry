package presentation.dayPartMenu

import model.DayPart
import toad.ViewEvent
import toad.ViewState

data class DayPartMenuUiState(
    val part: DayPart = DayPart.MORNING,
    var taskButtons: TaskButtonState = TaskButtonState(),
) : ViewState

sealed interface DayPartMenuEvent : ViewEvent
