package presentation.mainMenu

import data.calendar.CalendarRepository
import domain.ObserveAstronomicalContextUseCase
import toad.ActionDependencies

data class MainMenuActionDependencies(
    val calendarRepository: CalendarRepository,
    val observeAstronomicalContextUseCase: ObserveAstronomicalContextUseCase,
) : ActionDependencies()
