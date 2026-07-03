package presentation.mainMenu

import data.calendar.CalendarRepository
import domain.GetAstronomicalContextUseCase
import toad.ActionDependencies

data class MainMenuActionDependencies(
    val calendarRepository: CalendarRepository,
) : ActionDependencies() {
    val astronomicalContextUseCase = GetAstronomicalContextUseCase()
}
