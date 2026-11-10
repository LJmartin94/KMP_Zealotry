package presentation.example

import kotlinx.coroutines.flow.catch
import model.Example
import toad.ActionScope
import toad.ViewAction
import util.Logger

// Relies on ExampleActionDependencies, ExampleUiState

private const val TAG = "ExampleAction"

abstract class ExampleAction : ViewAction<ExampleActionDependencies, ExampleUiState, ExampleEvent>

// 'object' because it is a singleton & without constructor/params
data object ObserveExample : ExampleAction() {
    override suspend fun execute(
        dependencies: ExampleActionDependencies,
        scope: ActionScope<ExampleUiState, ExampleEvent>,
    ) {
        scope.setState { copy(isLoading = true) }
        dependencies.exampleRepository
            .observeCanonicalExample(Example.CanonicalKey.FIRST)
            .catch { e ->
                scope.setState {
                    copy(
                        isLoading = false,
                        error = ExampleError.TerminatedWithFailure(e.message.orEmpty()),
                    )
                }
            }.collect { example ->
                scope.setState { copy(id = example.id, toggle = example.toggle, isLoading = false) }
            }
    }
}

data class UpdateToggle(
    val newVal: Boolean,
) : ExampleAction() {
    override suspend fun execute(
        dependencies: ExampleActionDependencies,
        scope: ActionScope<ExampleUiState, ExampleEvent>,
    ) {
        val id = scope.currentState.id
        if (id == null) {
            scope.setState { copy(error = ExampleError.NotYetLoaded) }
            return
        }
        scope.withLoadingResult(
            setLoading = { copy(isLoading = it) },
            block = { dependencies.exampleRepository.updateToggle(id, newVal) },
            onSuccess = {
                // Flow will emit the updated value automatically via observeCanonicalExample
                // No special client-side onSuccess logic required.
            },
            onFailure = { result ->
                Logger.e(TAG, result) { "UpdateToggle failed" }
                scope.sendEvent(ExampleEvent.ShowTransientError(result.message.orEmpty()))
            },
        )
    }
}
