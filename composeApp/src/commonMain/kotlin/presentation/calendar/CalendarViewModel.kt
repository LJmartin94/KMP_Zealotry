package presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.calendar.CalendarRepository
import domain.calendar.CalendarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(private val calendarRepository: CalendarRepository) : ViewModel() {
    private val _calendarState = MutableStateFlow(CalendarState())
    val calendarState = _calendarState.asStateFlow()

    init {
        viewModelScope.launch {
            calendarRepository.updateFlow
                .collect { state: CalendarState ->
                    _calendarState.value = state
                }
        }
    }
}
