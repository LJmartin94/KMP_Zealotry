package presentation.screens.dayPartMenu

import data.screens.dayPartMenu.DayPartMenuUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class DayPartMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DayPartMenuUIState())
    val uiState = _uiState.asStateFlow()
}
