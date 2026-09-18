package presentation.example

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import toad.getViewModel
import util.Logger

// Relies on ExampleUiState, ExampleAction, ExampleViewModel

private const val TAG = "ExampleScreen"

@Composable
fun ExampleScreen() {
    val viewModel = getViewModel<ExampleViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
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
        Text(text = if (state.toggle) "on" else "off")
    }
}
