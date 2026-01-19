package presentation.example

import toad.ViewEvent
import toad.ViewState

sealed interface ExampleUiState: ViewState {
    data object Loading: ExampleUiState
    data class Error(val errorMessage: String): ExampleUiState
    data class Success(val id: String, val toggle: Boolean): ExampleUiState
}

sealed interface ExampleEvent: ViewEvent {
    data object NavigateBack: ExampleEvent
}