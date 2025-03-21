package presentation.components.tutorial

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import domain.tutorial.ToDoTask
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.delete
import zealotry.composeapp.generated.resources.star

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TaskView(
    task: ToDoTask,
    showActive: Boolean = true,
    onSelect: (ToDoTask) -> Unit,
    onComplete:  (ToDoTask, Boolean) -> Unit,
    onFavourite: (ToDoTask, Boolean) -> Unit,
    onDelete: (ToDoTask) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (showActive) onSelect(task)
                else onDelete(task)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onComplete(task, !task.completed) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.alpha(if (showActive) 1f else 0.5f),
                text = task.title,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                textDecoration = if (showActive) TextDecoration.None
                else TextDecoration.LineThrough
            )
        }
        IconButton(
            onClick = {
                if (showActive) onFavourite(task, !task.favourite)
                else onDelete(task)
            }
        ) {
            Icon(
                painter = painterResource(
                    if (showActive) Res.drawable.star
                    else Res.drawable.delete
                ),
                contentDescription = "Favorite Icon",
                tint = if (task.favourite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
        }
    }
}