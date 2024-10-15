package presentation.screens.dayPartMenu

import androidx.lifecycle.ViewModel
import data.screens.dayPartMenu.DayPartMenuUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DayPartMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DayPartMenuUIState())
    val uiState = _uiState.asStateFlow()
}
