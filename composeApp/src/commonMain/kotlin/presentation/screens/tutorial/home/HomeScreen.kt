package presentation.screens.tutorial.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import domain.tutorial.RequestState
import domain.tutorial.ToDoTask


class HomeScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = { Text(text = "Home")})
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    shape = RoundedCornerShape( size = 12.dp)
                ){
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Icon"
                    )
                }
            }
        ){ padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    )
            ){}
        }
    }
}

@Composable
fun DisplayTasks(
    modifier: Modifier = Modifier,
    tasks: RequestState<List<ToDoTask>>,
    showActive: Boolean = true,
    onSelect: ((ToDoTask) -> Unit)? = null,
    onComplete: (ToDoTask, Boolean) -> Unit,
    onFavourite: ((ToDoTask, Boolean) -> Unit)? = null,
    onDelete: ((ToDoTask) -> Unit)? = null,
){
    var showDialogue by remember { mutableStateOf(false) }
    var taskToDelete: ToDoTask? by remember { mutableStateOf(null) }

    if (showDialogue) {
        AlertDialog(
            title = {
                Text(text= "Delete", fontSize = MaterialTheme.typography.titleLarge.fontSize)
            },
            text = {
                Text(
                    text = "Are you sure you want to delete '${taskToDelete?.title}'?",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            },
            confirmButton = {
                Button(onClick = {
                    onDelete?.invoke(taskToDelete!!)
                    showDialogue = false
                    taskToDelete = null
                }) {
                    Text(text = "Yes - Delete it")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        taskToDelete = null
                        showDialogue = false
                    }
                ){
                    Text(text = "No - Cancel")
                }
            },
            onDismissRequest = {
                taskToDelete = null
                showDialogue = false
            },
        )
    }
}
