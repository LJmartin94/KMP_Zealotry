package presentation.dayPartMenu

import data.dayPartMenu.DayPartMenuRepository
import toad.ToadViewModel

class DayPartMenuViewModel(
    dayPartMenuRepository: DayPartMenuRepository,
) : ToadViewModel<DayPartMenuUiState, DayPartMenuEvent>(
        initialState = DayPartMenuUiState(),
    ) {
    override val dependencies =
        DayPartMenuActionDependencies(
            dayPartMenuRepository = dayPartMenuRepository,
        )

    init {
        dispatchAll(initialActions)
    }

    fun runAction(action: DayPartMenuAction) = dispatch(action)

    companion object {
        val initialActions =
            listOf<DayPartMenuAction>(
                // One-shot startup actions go here (e.g. load task buttons)
            )
    }
}
