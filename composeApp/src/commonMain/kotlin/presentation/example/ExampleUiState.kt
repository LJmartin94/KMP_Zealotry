package presentation.example

import toad.ViewEvent
import toad.ViewState

/**
 * The relevant data to be represented in the UI from the [model.Example] data model.
 * [id] is null until [ObserveExample] emits its first value — actions that require an id
 * should guard against this and no-op or emit an error if it is null.
 */
data class ExampleUiState(
    val id: String? = null,
    val toggle: Boolean = false,
    val isLoading: Boolean = false,
    val error: ExampleError? = null,
) : ViewState

sealed interface ExampleEvent : ViewEvent {
    data object NavigateBack : ExampleEvent
}

sealed interface ExampleError {
    data object NotYetLoaded : ExampleError
    data class Dynamic(val message: String) : ExampleError
}
