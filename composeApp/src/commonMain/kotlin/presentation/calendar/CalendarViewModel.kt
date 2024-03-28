package presentation.calendar

import data.calendar.CalendarRepository
import data.calendar.CalendarUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CalendarViewModel(private val calendarRepository: CalendarRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarUIState())
    val uiState = _uiState.asStateFlow()

    fun tryGetToday() = viewModelScope.launch {
        _uiState.update { state: CalendarUIState -> state.copy(
            isLoading = false,
            today = calendarRepository.getToday()) }
    }

}