package di

import data.AppDatabase
import data.DatabaseSeeder
import data.calendar.CalendarRepository
import data.calendar.CalendarRepositoryImpl
import data.createAppDatabase
import data.dayPartMenu.DayPartMenuRepository
import data.dayPartMenu.DayPartMenuRepositoryImpl
import data.example.ExampleRepository
import data.example.ExampleRepositoryImpl
import data.example.source.local.ExampleDao
import domain.ObserveAstronomicalContextUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.dayPartMenu.DayPartMenuViewModel
import presentation.example.ExampleViewModel
import presentation.mainMenu.MainMenuViewModel

fun initKoin(context: Any? = null) {
    startKoin {
        modules(
            module {
                singleOf(::CalendarRepositoryImpl) { bind<CalendarRepository>() }
                single { ObserveAstronomicalContextUseCase(get()) }
                factory { MainMenuViewModel(get(), get()) }

                singleOf(::DayPartMenuRepositoryImpl) { bind<DayPartMenuRepository>() }
                factory { DayPartMenuViewModel(get()) }

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
