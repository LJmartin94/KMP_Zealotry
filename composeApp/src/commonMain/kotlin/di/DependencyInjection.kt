package di

import data.example.ExampleRepository
import data.example.ExampleRepositoryImpl
import data.example.source.local.ExampleDao
import data.example.source.local.ExampleDaoImpl
import data.Database
import z.calendar.CalendarRepository
import z.screens.dayPartMenu.DayPartMenuRepository
import z.screens.mainMenu.MainMenuRepository
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.components.calendar.CalendarViewModel
import presentation.example.ExampleViewModel
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

                single { Database() }

                singleOf(::ExampleDaoImpl) { bind<ExampleDao>() }
                singleOf(::ExampleRepositoryImpl) { bind<ExampleRepository>() }
                factory { ExampleViewModel(get()) }
            },
        )
    }
}
