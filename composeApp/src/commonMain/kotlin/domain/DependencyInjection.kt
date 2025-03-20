package domain

import domain.calendar.CalendarRepository
import domain.screens.dayPartMenu.DayPartMenuRepository
import domain.screens.mainMenu.MainMenuRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.components.calendar.CalendarViewModel
import presentation.screens.dayPartMenu.DayPartMenuViewModel
import presentation.screens.mainMenu.MainMenuViewModel

fun initKoin() {
    startKoin {
        modules(
            module {
                single { MainMenuRepository() }
                factory { MainMenuViewModel(get()) }

                single { DayPartMenuRepository() }
                factory { DayPartMenuViewModel() }

                single { CalendarRepository() }
                factory { CalendarViewModel(get()) }
            },
        )
    }
}
