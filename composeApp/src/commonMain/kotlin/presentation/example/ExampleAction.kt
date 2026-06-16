package presentation.example

import data.example.Example
import toad.ActionScope
import toad.ViewAction

//Relies on ExampleUiState, ExampleActionDependencies

abstract class ExampleAction: ViewAction<ExampleActionDependencies, ExampleUiState, ExampleEvent>

data object LoadExample: ExampleAction() { // 'object' because it is a singleton & without constructor/params
    override suspend fun execute(
        dependencies: ExampleActionDependencies,
        scope: ActionScope<ExampleUiState, ExampleEvent>
    ) {
        scope.withLoadingResult(
            setLoading = { copy(isLoading = it) },
            block = { dependencies.exampleRepository.getCanonicalExample(Example.FIRST) },
            onSuccess = { result ->
                scope.setState { copy(id = result.id, toggle = result.toggle) }
            },
            onFailure = { result ->
                scope.setState { copy(error = result.message) }
            }
        )
    }
}

data class UpdateToggle(val newVal: Boolean): ExampleAction() {
    override suspend fun execute(
        dependencies: ExampleActionDependencies,
        scope: ActionScope<ExampleUiState, ExampleEvent>
    ) {
        scope.withLoadingResult(
            setLoading = { copy(isLoading = it) },
            block = { dependencies.exampleRepository.updateToggle(scope.currentState.id, newVal) },
            onSuccess = {
                scope.setState { copy(toggle = newVal) }
            },
            onFailure = { result ->
                scope.setState { copy(error = result.message) }
            }
        )
    }
}
