// TEMPORARY: deliberate violations proving the "only screens call getViewModel" and "no hand-built
// ViewModels" rules fire. Delete in phase 2.
package presentation.example

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import toad.getViewModel

@Composable
fun TemporaryKonsistViolationButtonTurnedScreen() {
    val fromNonScreen: ExampleViewModel = getViewModel()
    val builtByHand = ExampleViewModel(TODO())
    val fromAndroidx: ExampleViewModel = viewModel { TODO() }
}
