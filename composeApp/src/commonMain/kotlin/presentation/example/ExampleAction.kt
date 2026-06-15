package presentation.example

import data.HexStringId
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
            setLoading = { copy(isLoading = it)},
            block = { dependencies.exampleRepository.getExampleBySeedKey(Example.SEED_EXAMPLE_ONE) },
            onSuccess = { result ->
                scope.setState {
                    copy(id = result.id,
                        toggle = result.toggle)
                }
            },
            onFailure = { result ->
                scope.setState {
                    copy(error = result.toString())
                }
            }
        )
    }
}

data class UpdateToggle(val newVal: Boolean): ExampleAction() {
    override suspend fun execute(
        dependencies: ExampleActionDependencies,
        scope: ActionScope<ExampleUiState, ExampleEvent>
    ) {
        val id = HexStringId(scope.currentState.id) //TODO: I don't like importing this data layer implementation detail to presentation. Need to refactor
        scope.withLoadingResult(
            setLoading = { copy(isLoading = it)},
            block = { dependencies.exampleRepository.updateToggle(id, newVal) },
            onSuccess = { _ ->
                scope.setState {
                    copy(toggle = newVal)
                }
            },
            onFailure = { result ->
                scope.setState {
                    copy(error = result.toString())
                }
            }
        )
    }

}