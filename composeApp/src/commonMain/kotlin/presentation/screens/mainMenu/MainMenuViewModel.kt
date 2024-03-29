package presentation.screens.mainMenu

import data.screens.mainMenu.MainMenuUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class MainMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainMenuUIState())
    val uiState = _uiState.asStateFlow()
}