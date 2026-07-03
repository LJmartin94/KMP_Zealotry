package data.calendar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Clock
import kotlin.time.Instant

class CalendarRepositoryImpl : CalendarRepository {
    private val _updateFlow = MutableStateFlow(Clock.System.now())
    override val updateFlow: StateFlow<Instant> = _updateFlow.asStateFlow()

    override fun refresh() {
        _updateFlow.value = Clock.System.now()
    }
}
