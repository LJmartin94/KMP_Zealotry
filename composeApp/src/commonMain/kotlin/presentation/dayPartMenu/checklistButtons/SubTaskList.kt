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
import zealotry.composeapp.generated.resources.ic_baseline_fast_forward_24
import zealotry.composeapp.generated.resources.ic_baseline_fast_forward_24_inv
import zealotry.composeapp.generated.resources.skip

// Thinking about how to structure this with future customisability in mind:
// fun ChecklistItem(name: String, index: Int, original: Boolean = false, action: () -> Unit = {}){}

// itemNames is Ordered, Modifiable, State Like (state should be passed from parent)
@Composable
@OptIn(ExperimentalResourceApi::class)
fun SubTaskList(
    // Can maybe make an assert that itemIcons contains value for Default
    defaultIcon: Chiaroscuro,
    taskNames: MutableList<String> = mutableListOf(),
    taskFunctions: MutableMap<String, () -> Unit> = mutableMapOf(),
    taskIcons: MutableMap<String, Chiaroscuro> = mutableMapOf(),
): List<@Composable () -> Unit> {
    val done: String = stringResource(Res.string.done)
    val skip: String = stringResource(Res.string.skip)
    val defaultAction: () -> Unit

    if (taskNames.isEmpty()) taskNames.add(done) // When no subtasks, 'done' is default subtask
    if (taskNames.size > 1) { // When multiple subtasks, done is specified separately
        defaultAction = { defaultSubtask() }
        taskNames.addAll(listOf(done, skip))
    } else { // When only one subtask, it functions as 'done' subtask
        defaultAction = { defaultDone() }
        taskNames.add(skip)
    }

    // Overwrite behaviour of Done and Skip, if they were specified by input
    taskFunctions[done] = { defaultDone() }
    taskFunctions[skip] = { defaultSkip() }
    taskIcons[skip] =
        Chiaroscuro(
            Res.drawable.ic_baseline_fast_forward_24,
            Res.drawable.ic_baseline_fast_forward_24_inv,
        )
    return taskNames.map { name ->
        @Composable {
            CustomExtendedFAB(
                text = { Text(name) },
                onClick = taskFunctions[name] ?: defaultAction,
                modifier = Modifier.fillMaxWidth(1f),
                icon = {
                    Icon(
                        painter = (taskIcons[name] ?: defaultIcon).getPainter(),
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
