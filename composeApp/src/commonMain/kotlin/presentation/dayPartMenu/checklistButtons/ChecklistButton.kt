package presentation.dayPartMenu.checklistButtons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.dayPartMenu.morningButtons.MorningButtons
import presentation.dayPartMenu.morningButtons.toBundle
import presentation.reusableUi.Chiaroscuro

data class ChecklistButtonState(
    val mainIcon: Chiaroscuro,
    val mainText: String,
    val isActive: Boolean = true,
    val isExpanded: Boolean = false,
    val completeTime: String? = null,
    val durationInSeconds: Number = 0,
    val iterationsMeasured: Number = 0,
    val subtaskList: MutableList<String>,
    val subtasksCompleted: MutableList<String> = mutableListOf(),
    val subtaskIcons: MutableMap<String, Chiaroscuro>,
)

@Composable
fun ChecklistButton(button: MorningButtons) =
    Box(modifier = Modifier.wrapContentHeight()) {
        val subtasks = SubTaskList(button.toBundle().chiaro)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            MainTaskButton(button.toBundle())
            subtasks.forEach { task -> task.invoke() }
        }
    }
