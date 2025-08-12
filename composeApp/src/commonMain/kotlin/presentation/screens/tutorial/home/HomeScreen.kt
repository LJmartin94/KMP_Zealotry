package presentation.screens.tutorial.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.tutorial.RequestState
import domain.tutorial.TaskAction
import domain.tutorial.ToDoTask
import libs.mvvm.getViewModel
import presentation.components.tutorial.ErrorScreen
import presentation.components.tutorial.LoadingScreen
import presentation.components.tutorial.TaskView
import tutorial.NavDestination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateTo: (NavDestination) -> Unit //TODO: Passing task feels wrong, should probably be handled with event callback. This is an intermediate solution.
)
{
    val viewModel = getViewModel<HomeViewModel>() //TODO: possibly VM should be passed from Navigation graph, not fetched?
    val activeTasks by viewModel.activeTasks
    val completedTasks by viewModel.completedTasks

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Home")})
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //TODO: Clear loaded task if any
                    onNavigateTo(NavDestination.Task)
                },
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
        ){
            DisplayTasks(
                modifier = Modifier.weight(1f),
                tasks = activeTasks,
                onSelect = { selectedTask ->
                    //TODO: Use selectedTask
                    onNavigateTo(NavDestination.Task)
                },
                onFavourite = { task, isFavourite ->
                    viewModel.setAction(
                        action = TaskAction.SetFavourite(task, isFavourite)
                    )
                },
                onComplete = { task, completed ->
                    viewModel.setAction(
                        action = TaskAction.SetCompleted(task, completed)
                    )
                },
            )
            Spacer(modifier = Modifier.height(24.dp))
            DisplayTasks(
                modifier = Modifier.weight(1f),
                tasks = completedTasks,
                showActive = false,
                onComplete = { task, completed ->
                    viewModel.setAction(
                        action = TaskAction.SetCompleted(task, completed)
                    )
                },
                onDelete = {task ->
                    viewModel.setAction(
                        action = TaskAction.Delete(task)
                    )
                },
            )
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
) {
    var showDialogue by remember { mutableStateOf(false) }
    var taskToDelete: ToDoTask? by remember { mutableStateOf(null) }

    if (showDialogue) {
        AlertDialog(
            title = {
                Text(text = "Delete", fontSize = MaterialTheme.typography.titleLarge.fontSize)
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
                ) {
                    Text(text = "No - Cancel")
                }
            },
            onDismissRequest = {
                taskToDelete = null
                showDialogue = false
            },
        )
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = if (showActive) "Active Tasks" else "Completed Tasks",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        tasks.DisplayResult(
            onLoading = { LoadingScreen() },
            onError = { ErrorScreen(message = it) },
            onSuccess = {
                if (it.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 24.dp)) {
                        items(
                            items = it,
                            key = { task -> task._id.toHexString() }
                        ) { task ->
                            TaskView(
                                showActive = showActive,
                                task = task,
                                onSelect = { onSelect?.invoke(it) },
                                onComplete = { selectedTask, completed ->
                                    onComplete(
                                        selectedTask,
                                        completed
                                    )
                                },
                                onFavourite = { selectedTask, favourite ->
                                    onFavourite?.invoke(
                                        selectedTask,
                                        favourite
                                    )
                                },
                                onDelete = { selectedTask ->
                                    taskToDelete = selectedTask
                                    showDialogue = true
                                },
                            )
                        }
                    }
                } else {
                    ErrorScreen(message = "Task list is empty")
                }
            }
        )
    }
}
