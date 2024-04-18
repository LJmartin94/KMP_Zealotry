package presentation.screens.mainMenu

import data.calendar.CalendarRepository
import data.calendar.CalendarState
import data.screens.mainMenu.MainMenuUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MainMenuViewModel(private val calendarRepository: CalendarRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MainMenuUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            calendarRepository.updateFlow
                .collect { state: CalendarState ->
                    _uiState.value = _uiState.value.copy(
                        dayName = state.dayOfWeek,
                        backgroundSeason = state.seasonInfo.currentSeason
                    )
                }
        }
    }
}