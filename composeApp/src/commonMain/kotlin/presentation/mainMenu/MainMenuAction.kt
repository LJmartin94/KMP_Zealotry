package presentation.mainMenu

import toad.ActionScope
import toad.ViewAction

abstract class MainMenuAction : ViewAction<MainMenuActionDependencies, MainMenuUiState, MainMenuEvent>

data object ObserveCalendarContext : MainMenuAction() {
    override suspend fun execute(
        dependencies: MainMenuActionDependencies,
        scope: ActionScope<MainMenuUiState, MainMenuEvent>,
    ) {
        dependencies.observeAstronomicalContextUseCase().collect { context ->
            scope.setState {
                copy(
                    dayOfWeek = context.dayOfWeek,
                    festiveDay = context.festiveDay,
                    dayOfSeason = context.dayOfSeason,
                    currentSeason = context.season,
                )
            }
        }
    }
}

// Fired on ON_START (app foregrounded or returning from a nested screen) to correct
// any clock drift that occurred while the scheduling loop was delayed by Doze mode.
data object RefreshCalendarContext : MainMenuAction() {
    override suspend fun execute(
        dependencies: MainMenuActionDependencies,
        scope: ActionScope<MainMenuUiState, MainMenuEvent>,
    ) {
        dependencies.calendarRepository.refresh()
    }
}
