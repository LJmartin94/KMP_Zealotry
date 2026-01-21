package presentation.example

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import toad.getViewModel

// Relies on ExampleUiState, ExampleAction, ExampleViewModel

@Composable
fun ExampleScreen(){
    val viewModel = getViewModel<ExampleViewModel>()
    val state by viewModel.state.collectAsState()
    StatelessExampleScreen(
        state = state,
        onAction = viewModel::runAction,
    )
}

@Composable
fun StatelessExampleScreen(
    state: ExampleUiState,
    onAction: (ExampleAction) -> Unit,
){
    Button(
        onClick = { onAction(UpdateToggle(!state.toggle))}
    ){
        Text( text = if (state.toggle) "on" else "off")
    }
}