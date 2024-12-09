package presentation.screens.dayPartMenu.checklistButtons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.screens.dayPartMenu.morningButtons.MorningButtons
import presentation.screens.dayPartMenu.morningButtons.toBundle

@Composable
fun ChecklistButton(button: MorningButtons) = Box(modifier = Modifier.wrapContentHeight()){
    val subtasks = SubTaskList(button.toBundle().chiaro, itemNames = listOf("just", "random", "stuff"))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ){
        MainTaskButton(button.toBundle())
        subtasks.forEach { task -> task.invoke() }
    }
}