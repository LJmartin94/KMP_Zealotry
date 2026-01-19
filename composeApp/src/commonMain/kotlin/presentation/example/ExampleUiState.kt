package presentation.example

import toad.ViewEvent
import toad.ViewState

/**
 * The relevant data to be represented in the UI from the [data.example.Example] data model.
 * All values must be explicitly initialised, unless fetching the state resulted in an error.
 */
data class ExampleUiState (
    val id: String,
    val toggle: Boolean,
    val isLoading: Boolean,
): ViewState {
    private var error: String? = null

    constructor(error: Throwable) : this(
        id = "",
        toggle = false,
        isLoading = false,
    ) {
        this.error = error.message.toString()
    }
}

// Old implementation:

//sealed interface ExampleUiState: ViewState {
//    data object Loading: ExampleUiState
//    data class Error(val errorMessage: String): ExampleUiState
//    data class Success(val id: String, val toggle: Boolean): ExampleUiState
//}

sealed interface ExampleEvent: ViewEvent {
    data object NavigateBack: ExampleEvent
}