package presentation.mainMenu

import data.calendar.getFestiveDay
import toad.ActionScope
import toad.ViewAction

abstract class MainMenuAction : ViewAction<MainMenuActionDependencies, MainMenuUiState, MainMenuEvent>

data object ObserveCalendarContext : MainMenuAction() {
    override suspend fun execute(
        dependencies: MainMenuActionDependencies,
        scope: ActionScope<MainMenuUiState, MainMenuEvent>,
    ) {
        dependencies.calendarRepository.updateFlow
            .collect { state ->
                scope.setState {
                    copy(
                        dayOfWeek = state.dayOfWeek,
                        festiveDay = state.seasonInfo.getFestiveDay(),
                        dayOfSeason = state.seasonInfo.dayOfTheSeason,
                        currentSeason = state.seasonInfo.currentSeason,
                    )
                }
            }
    }
}
