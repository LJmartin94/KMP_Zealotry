package presentation.calendar

import data.calendar.CalendarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CalendarViewModel(private val calendarRepository: CalendarRepository) : ViewModel() {
    private val _currentDay = MutableStateFlow((DayOfWeek.MONDAY))
    val currentDay = _currentDay.asStateFlow()

    init {
        viewModelScope.launch {
            calendarRepository.zealotryDayFlow
                .collect { day ->
                    _currentDay.value = day
                }
        }
    }
}