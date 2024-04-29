package di

import data.calendar.CalendarRepository
import data.screens.dayPartMenu.DayPartMenuRepository
import data.screens.mainMenu.MainMenuRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.calendar.CalendarViewModel
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
