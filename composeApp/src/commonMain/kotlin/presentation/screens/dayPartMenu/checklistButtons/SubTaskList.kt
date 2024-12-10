package presentation.screens.dayPartMenu.checklistButtons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.reusableUi.Chiaroscuro
import presentation.reusableUi.CustomExtendedFAB
import presentation.style.ColourCompositionLocal
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.done
import zealotry.composeapp.generated.resources.skip

// Thinking about how to structure this with future customisability in mind:
// fun ChecklistItem(name: String, index: Int, original: Boolean = false, action: () -> Unit = {}){}

// itemNames is Ordered, Modifiable, State Like (state should be passed from parent)
@Composable
@OptIn(ExperimentalResourceApi::class)
fun SubTaskList(
    // Can maybe make an assert that itemIcons contains value for Default
    defaultIcon: Chiaroscuro,
    itemNames: List<String> = listOf(stringResource(Res.string.done)),
    itemFunctions: Map<String, () -> Unit> = emptyMap(),
    itemIcons: Map<String, Chiaroscuro> = emptyMap(),
): List<@Composable () -> Unit> {
    val done: String = stringResource(Res.string.done)
    val skip: String = stringResource(Res.string.skip)
    val isMulti = itemNames.size > 1
    val defaultAction: () -> Unit =
        if (isMulti) {
            { defaultSubtask() }
        } else {
            { defaultDone() }
        }
    val taskNames: List<String> = if (isMulti) itemNames + listOf(done, skip) else itemNames + listOf(skip)
    val taskFunctions: MutableMap<String, () -> Unit> = itemFunctions.toMutableMap()
    taskFunctions[done] = { defaultDone() }
    taskFunctions[skip] = { defaultSkip() }
    return taskNames.map { name ->
        @Composable {
            CustomExtendedFAB(
                text = { Text(name) },
                onClick = taskFunctions[name] ?: defaultAction,
                modifier = Modifier.fillMaxWidth(1f),
                icon = {
                    Icon(
                        painter = (itemIcons[name] ?: defaultIcon).getPainter(),
                        contentDescription = null,
                    )
                },
                backgroundColor = ColourCompositionLocal.current.background,
                contentAlignment = Alignment.CenterStart,
            )
        }
    }
}

private fun defaultSubtask() {}

private fun defaultDone() {}

private fun defaultSkip() {}
