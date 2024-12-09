package presentation.screens.dayPartMenu.checklistButtons

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.reusableUi.ChiaroscuroDrawable
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.done
import zealotry.composeapp.generated.resources.skip

// Thinking about how to structure this with future customisability in mind:
// fun ChecklistItem(name: String, index: Int, original: Boolean = false, action: () -> Unit = {}){}

// itemNames is Ordered, Modifiable, State Like (state should be passed from parent)
@Composable
@OptIn(ExperimentalResourceApi::class)
fun SubTaskList(
    defaultIcon: ChiaroscuroDrawable, //Can maybe make an assert that itemIcons contains value for Default
    itemNames: List<String> = listOf(stringResource(Res.string.done)),
    itemFunctions: Map<String, () -> Unit> = emptyMap(),
    itemIcons: Map<String, ChiaroscuroDrawable> = emptyMap(),
) : List<@Composable ()-> Unit> {
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
        @Composable {MainTaskButton(
                    chiaro = itemIcons[name] ?: defaultIcon,
                    text = name,
                    time = "",
                    onClick = taskFunctions[name] ?: defaultAction,
                )}
        }
}

private fun defaultSubtask()  {}

private fun defaultDone()  {}

private fun defaultSkip()  {}
