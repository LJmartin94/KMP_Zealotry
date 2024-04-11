package presentation.calendar

import data.calendar.CalendarRepository
import data.calendar.CalendarUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CalendarViewModel(private val calendarRepository: CalendarRepository) : ViewModel() {

//    private val _uiState = MutableStateFlow(CalendarUIState())
//    val uiState = _uiState.asStateFlow()

    private val _currentDay = MutableStateFlow((DayOfWeek.MONDAY))
    val currentDay = _currentDay.asStateFlow()

    init{
        calendarRepository.getToday()
            .onEach { day -> _currentDay.emit(day) }
            .launchIn(viewModelScope)
    }

//    fun tryGetToday() = viewModelScope.launch {
//        _uiState.update { state: CalendarUIState -> state.copy(
//            isLoading = false,
//            today = calendarRepository.getToday()
//        ) }
//    }

}