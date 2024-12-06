package presentation.screens.dayPartMenu.checklistButtons

import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.reusableUi.ChiaroscuroDrawable
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.done
import zealotry.composeapp.generated.resources.skip

// Thinking about how to structure this with future customisability in mind:
// fun ChecklistItem(name: String, index: Int, original: Boolean = false, action: () -> Unit = {}){}

// itemNames is Ordered, Modifiable, State Like (state should be passed from parent)
@OptIn(ExperimentalResourceApi::class)
fun ChecklistSubMenu(
    itemNames: List<String> = emptyList(),
    itemFunctions: Map<String, () -> Unit> = emptyMap(),
    itemIcons: Map<String, ChiaroscuroDrawable> = emptyMap(),
) {
    val done: String = Res.string.done.toString()
    val skip: String = Res.string.skip.toString()
    val isMulti = itemNames.size > 1
    val defaultAction: () -> Unit =
        if (isMulti) {
            { defaultSubtask() }
        } else {
            { defaultDone() }
        }
    val taskNames: List<String> = if (isMulti) itemNames + done + skip else itemNames + skip
    val taskFunctions: MutableMap<String, () -> Unit> = itemFunctions.toMutableMap()
    taskFunctions[done] = { defaultDone() }
    taskFunctions[skip] = { defaultSkip() }
    val taskMap: Map<String, () -> Unit> =
        taskNames.associateWith { entry -> (taskFunctions[entry] ?: defaultAction) }
}

private fun defaultSubtask()  {}

private fun defaultDone()  {}

private fun defaultSkip()  {}
