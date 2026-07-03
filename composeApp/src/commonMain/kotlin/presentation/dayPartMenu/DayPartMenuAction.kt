package presentation.dayPartMenu

import data.dayPartMenu.DayPart
import toad.ActionScope
import toad.ViewAction

abstract class DayPartMenuAction : ViewAction<DayPartMenuActionDependencies, DayPartMenuUiState, DayPartMenuEvent>

data class SetDayPart(val part: DayPart) : DayPartMenuAction() {
    override suspend fun execute(
        dependencies: DayPartMenuActionDependencies,
        scope: ActionScope<DayPartMenuUiState, DayPartMenuEvent>,
    ) {
        val newPart = part
        scope.setState { copy(part = newPart) }
    }
}
