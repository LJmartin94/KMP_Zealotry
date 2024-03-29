package presentation.templateComponent

import data.templateComponent.TemplateComponentUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class TemplateComponentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TemplateComponentUIState())
    val uiState = _uiState.asStateFlow()
}