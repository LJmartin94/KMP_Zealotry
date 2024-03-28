package di

import data.calendar.CalendarRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.calendar.CalendarViewModel

fun initKoin() {
    startKoin {
        modules(
            module{
                single { CalendarRepository()}
                factory { CalendarViewModel(get())}
            }
        )
    }
}