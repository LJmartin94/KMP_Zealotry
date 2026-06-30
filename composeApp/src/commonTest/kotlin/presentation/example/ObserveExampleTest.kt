package presentation.example

import app.cash.turbine.test
import data.example.Example
import data.example.ExampleRepository
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import toad.ActionScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ObserveExampleTest {
    private fun makeScope(initialState: ExampleUiState = ExampleUiState()): ActionScope<ExampleUiState, ExampleEvent> {
        return ActionScope(MutableStateFlow(initialState), Channel(Channel.UNLIMITED))
    }

    @Test
    fun `when flow emits example state is updated with example data`() = runTest {
        val example = Example(id = "6ba7b810-9dad-11d1-80b4-00c04fd430c8", toggle = true)
        val repo = mock<ExampleRepository>()
        every { repo.observeCanonicalExample(any()) } returns flowOf(example)
        val deps = ExampleActionDependencies(exampleRepository = repo)
        val stateFlow = MutableStateFlow(ExampleUiState())
        val scope = ActionScope<ExampleUiState, ExampleEvent>(stateFlow, Channel(Channel.UNLIMITED))

        stateFlow.test {
            awaitItem() // initial state

            ObserveExample.execute(deps, scope)

            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val finalState = awaitItem()
            assertEquals(example.id, finalState.id)
            assertEquals(true, finalState.toggle)
            assertEquals(false, finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when flow throws error state is set and loading is cleared`() = runTest {
        val repo = mock<ExampleRepository>()
        every { repo.observeCanonicalExample(any()) } returns flow { throw RuntimeException("upstream error") }
        val deps = ExampleActionDependencies(exampleRepository = repo)
        val stateFlow = MutableStateFlow(ExampleUiState())
        val scope = ActionScope<ExampleUiState, ExampleEvent>(stateFlow, Channel(Channel.UNLIMITED))

        stateFlow.test {
            awaitItem() // initial state

            ObserveExample.execute(deps, scope)

            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val errorState = awaitItem()
            assertNotNull(errorState.error)
            assertEquals(false, errorState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
