package presentation.example

import androidx.lifecycle.viewModelScope
import data.example.ExampleRepository
import toad.ToadViewModel

//Relies on ExampleUiState, ExampleActionDependencies, ExampleAction

class ExampleViewModel (
    private val repository: ExampleRepository,
) : ToadViewModel<ExampleUiState, ExampleEvent>(
    initialState = ExampleUiState(
        id = "example",
        toggle = false,
        isLoading = false
    )
){
    override val dependencies = ExampleActionDependencies(
        exampleRepository = repository,
        coroutineScope = viewModelScope,
    )

    init {
        dispatchAll(initialActions)
    }

    fun runAction(action: ExampleAction) = dispatch(action)

    companion object{
        // Instead of passing the initial actions,
        // we have a single source of truth for what & how state should be restored
        val initialActions = listOf<ExampleAction>(
            LoadExample,
        )
    }
}