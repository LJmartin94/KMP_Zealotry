package presentation.example

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import toad.ViewEvent
import toad.ViewState
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.toggle_error

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

/**
 * Deliberately holds unresolved error data rather than a localized [String], resolved later by
 * [toResourceString] at the Composable call site. Alternatives rejected:
 *
 * - Resolving via `stringResource()` directly in Action is impossible, since
 *   `stringResource()` requires `@Composable` - not the case for [toad.ViewAction]s.
 *
 * - Resolving via `getString()`, the suspending non-Composable option: breaks TOAD's core
 *   "Actions are testable in isolation" guarantee (see [toad.ViewAction]'s KDoc) without additional plugins.
 *
 * Keeping resolution in the Composable layer avoids both problems and matches the existing
 * precedent in presentation/resourceComposition/Strings.kt.
 */
sealed interface ExampleError {
    data object NotYetLoaded : ExampleError
    data class Dynamic(val message: String) : ExampleError
}

@Composable
fun ExampleError.toResourceString(): String =
    when (this) {
        ExampleError.NotYetLoaded -> stringResource(Res.string.toggle_error)
        is ExampleError.Dynamic -> message
    }
