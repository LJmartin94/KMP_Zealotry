package di

import data.calendar.CalendarRepository
import data.screens.mainMenu.MainMenuRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.calendar.CalendarViewModel
import presentation.screens.mainMenu.MainMenuViewModel

fun initKoin() {
    startKoin {
        modules(
            module {
                single { MainMenuRepository() }
                factory { MainMenuViewModel() }

                single { CalendarRepository()}
                factory { CalendarViewModel(get())}
            }
        )
    }
}