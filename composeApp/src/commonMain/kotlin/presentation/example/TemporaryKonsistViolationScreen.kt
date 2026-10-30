// TEMPORARY: deliberate violation proving the "one ViewModel per view" rule fires. Delete in phase 2.
package presentation.example

import androidx.compose.runtime.Composable
import toad.getViewModel

@Composable
fun TemporaryKonsistViolationScreen() {
    val first = getViewModel<ExampleViewModel>()
    val second = getViewModel<ExampleViewModel>()
    val third: ExampleViewModel = getViewModel() // inferred form, must also be counted
}
