package presentation.mainMenu

import data.calendar.CalendarRepository
import domain.ObserveAstronomicalContextUseCase
import toad.ToadViewModel

class MainMenuViewModel(
    calendarRepository: CalendarRepository,
    observeAstronomicalContextUseCase: ObserveAstronomicalContextUseCase,
) : ToadViewModel<MainMenuUiState, MainMenuEvent>(
        initialState = MainMenuUiState(),
    ) {
    override val dependencies =
        MainMenuActionDependencies(
            calendarRepository = calendarRepository,
            observeAstronomicalContextUseCase = observeAstronomicalContextUseCase,
        )

    init {
        dispatchAll(initialActions)
        // ObserveCalendarContext runs indefinitely (collects a Flow), so it is dispatched separately
        // rather than included in initialActions, which are run sequentially via dispatchAll.
        dispatch(ObserveCalendarContext)
    }

    fun runAction(action: MainMenuAction) = dispatch(action)

    fun onForeground() = dispatch(RefreshCalendarContext)

    companion object {
        val initialActions =
            listOf<MainMenuAction>(
                // One-shot startup actions go here (e.g. load user preferences)
            )
    }
}
