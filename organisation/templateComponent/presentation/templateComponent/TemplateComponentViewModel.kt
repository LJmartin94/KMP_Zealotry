package presentation.templateComponent

import androidx.lifecycle.ViewModel
import data.templateComponent.TemplateComponentUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TemplateComponentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TemplateComponentUIState())
    val uiState = _uiState.asStateFlow()
}