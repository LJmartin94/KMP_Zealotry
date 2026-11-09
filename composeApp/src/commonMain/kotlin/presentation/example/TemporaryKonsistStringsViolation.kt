// TEMPORARY: proves the hardcoded-strings rule follows the enclosing call, not a line count.
// Delete in phase 2.
package presentation.example

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.generic_hello

// Allowed: a developer-facing message that wraps over more lines than the old 2-line look-back.
fun temporaryWrappedMessage(): Nothing =
    error(
        "A long developer-facing message " +
            "that wraps over several lines " +
            "and must still be allowed",
    )

@Composable
fun temporaryLeak(flag: Boolean) {
    require(flag) { "Allowed: developer-facing" }
    // Violation: the call above is already closed, so this literal must be flagged.
    println(stringResource(Res.string.generic_hello))
}
