package presentation.example

import data.example.ExampleRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import toad.ActionScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UpdateToggleTest {
    private fun makeScope(initialState: ExampleUiState): ActionScope<ExampleUiState, ExampleEvent> =
        ActionScope(MutableStateFlow(initialState), Channel(Channel.UNLIMITED))

    @Test
    fun `when id is null sets error and does not call repository`() =
        runTest {
            val repo = mock<ExampleRepository>()
            val deps = ExampleActionDependencies(exampleRepository = repo)
            val scope = makeScope(ExampleUiState(id = null))

            UpdateToggle(newVal = true).execute(deps, scope)

            assertNotNull(scope.currentState.error)
            verifySuspend(exactly(0)) { repo.updateToggle(any(), any()) }
        }

    @Test
    fun `when id is set and update succeeds loading is cleared and no error`() =
        runTest {
            val repo = mock<ExampleRepository>()
            everySuspend { repo.updateToggle(any(), any()) } returns Result.success(Unit)
            val deps = ExampleActionDependencies(exampleRepository = repo)
            val scope = makeScope(ExampleUiState(id = "6ba7b810-9dad-11d1-80b4-00c04fd430c8"))

            UpdateToggle(newVal = true).execute(deps, scope)

            assertEquals(false, scope.currentState.isLoading)
            assertNull(scope.currentState.error)
        }

    @Test
    fun `when id is set and update fails error state is set`() =
        runTest {
            val repo = mock<ExampleRepository>()
            everySuspend { repo.updateToggle(any(), any()) } returns Result.failure(RuntimeException("network error"))
            val deps = ExampleActionDependencies(exampleRepository = repo)
            val scope = makeScope(ExampleUiState(id = "6ba7b810-9dad-11d1-80b4-00c04fd430c8"))

            UpdateToggle(newVal = false).execute(deps, scope)

            assertEquals("network error", scope.currentState.error)
            assertEquals(false, scope.currentState.isLoading)
        }
}
