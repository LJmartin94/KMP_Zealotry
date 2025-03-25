package domain

import data.tutorial.MongoDB
import domain.calendar.CalendarRepository
import domain.screens.dayPartMenu.DayPartMenuRepository
import domain.screens.mainMenu.MainMenuRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.components.calendar.CalendarViewModel
import presentation.screens.dayPartMenu.DayPartMenuViewModel
import presentation.screens.mainMenu.MainMenuViewModel
import presentation.screens.tutorial.home.HomeViewModel

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

                mongoModule //from tutorial
            },
        )
    }
}

val mongoModule = module {
    single { MongoDB() }
    factory { HomeViewModel(get()) }
}