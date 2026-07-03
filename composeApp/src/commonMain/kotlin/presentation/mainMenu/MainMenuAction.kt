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
