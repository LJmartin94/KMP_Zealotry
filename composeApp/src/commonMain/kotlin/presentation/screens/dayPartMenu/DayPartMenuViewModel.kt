package presentation.screens.dayPartMenu

import androidx.lifecycle.ViewModel
import data.screens.dayPartMenu.DayPart
import data.screens.dayPartMenu.DayPartMenuUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DayPartMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DayPartMenuUIState())
    val uiState = _uiState.asStateFlow()

    fun setDayPart(part: DayPart) {
        _uiState.update { currentState ->
            currentState.copy(part = part)
        }
    }
}
