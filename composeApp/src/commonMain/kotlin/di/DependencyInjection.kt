package di

import data.AppDatabase
import data.DatabaseSeeder
import data.createAppDatabase
import data.example.ExampleRepository
import data.example.ExampleRepositoryImpl
import data.example.source.local.ExampleDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.components.calendar.CalendarViewModel
import presentation.example.ExampleViewModel
import presentation.screens.dayPartMenu.DayPartMenuViewModel
import presentation.screens.mainMenu.MainMenuViewModel
import z.calendar.CalendarRepository
import z.screens.dayPartMenu.DayPartMenuRepository
import z.screens.mainMenu.MainMenuRepository

fun initKoin(context: Any? = null) {
    startKoin {
        modules(
            module {
                single { MainMenuRepository() }
                factory { MainMenuViewModel(get()) }

                single { DayPartMenuRepository() }
                factory { DayPartMenuViewModel() }

                single { CalendarRepository() }
                factory { CalendarViewModel(get()) }

                single {
                    createAppDatabase(context).also { db ->
                        CoroutineScope(Dispatchers.Default).launch {
                            DatabaseSeeder.seedAll(db)
                        }
                    }
                }

                single<ExampleDao> { get<AppDatabase>().exampleDao() }
                singleOf(::ExampleRepositoryImpl) { bind<ExampleRepository>() }
                factory { ExampleViewModel(get()) }
            },
        )
    }
}
