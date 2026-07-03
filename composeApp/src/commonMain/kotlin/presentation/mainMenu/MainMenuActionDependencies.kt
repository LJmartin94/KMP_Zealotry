package presentation.mainMenu

import data.calendar.CalendarRepository
import toad.ActionDependencies

data class MainMenuActionDependencies(
    val calendarRepository: CalendarRepository,
) : ActionDependencies()
