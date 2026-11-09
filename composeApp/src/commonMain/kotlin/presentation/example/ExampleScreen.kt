package presentation.example

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import presentation.reusableUi.LocalSnackbarHostState
import toad.getViewModel
import util.Logger
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.generic_off
import zealotry.composeapp.generated.resources.generic_on

// Relies on ExampleUiState, ExampleAction, ExampleViewModel

private const val TAG = "ExampleScreen"

@Composable
fun ExampleScreen() {
    val viewModel = getViewModel<ExampleViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ExampleEvent.ShowTransientError -> {
                    // launch in separate coroutine so showSnackbar() doesn't block the loop from
                    // collecting other events; dev-side logging handled in onFailure
                    coroutineScope.launch { snackbarHostState.showSnackbar(event.message) }
                }
                ExampleEvent.NavigateBack -> Unit // Not wired up yet
            }
        }
    }

    StatelessExampleScreen(
        state = state,
        onAction = viewModel::runAction,
    )
}

@Composable
fun StatelessExampleScreen(
    state: ExampleUiState,
    onAction: (ExampleAction) -> Unit,
) {
    Button(
        onClick = {
            Logger.d(TAG) { "id of toggle: ${state.id}" }
            onAction(UpdateToggle(!state.toggle))
        },
    ) {
        Text(
            text =
                if (state.toggle) {
                    stringResource(Res.string.generic_on)
                } else {
                    stringResource(Res.string.generic_off)
                },
        )
    }
}
