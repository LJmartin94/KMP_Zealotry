package presentation.example

import data.example.Example
import kotlinx.coroutines.flow.catch
import toad.ActionScope
import toad.ViewAction

//Relies on ExampleUiState, ExampleActionDependencies

abstract class ExampleAction: ViewAction<ExampleActionDependencies, ExampleUiState, ExampleEvent>

data object ObserveExample : ExampleAction() { // 'object' because it is a singleton & without constructor/params
    override suspend fun execute(
        dependencies: ExampleActionDependencies,
        scope: ActionScope<ExampleUiState, ExampleEvent>
    ) {
        scope.setState { copy(isLoading = true) }
        dependencies.exampleRepository.observeCanonicalExample(Example.FIRST)
            .catch { e -> scope.setState { copy(isLoading = false, error = e.message) } }
            .collect { example ->
                scope.setState { copy(id = example.id, toggle = example.toggle, isLoading = false) }
            }
    }
}

data class UpdateToggle(val newVal: Boolean): ExampleAction() {
    override suspend fun execute(
        dependencies: ExampleActionDependencies,
        scope: ActionScope<ExampleUiState, ExampleEvent>
    ) {
        val id = scope.currentState.id
        if (id == null) {
            scope.setState { copy(error = "Cannot update toggle: example not yet loaded.") }
            return
        }
        scope.withLoadingResult(
            setLoading = { copy(isLoading = it) },
            block = { dependencies.exampleRepository.updateToggle(id, newVal) },
            onSuccess = {
                // Flow will emit the updated value automatically via observeCanonicalExample
            },
            onFailure = { result ->
                scope.setState { copy(error = result.message) }
            }
        )
    }
}
