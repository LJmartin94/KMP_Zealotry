package presentation.screens.tutorial.task

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.tutorial.TaskAction
import domain.tutorial.ToDoTask
import libs.mvvm.getViewModel
import tutorial.NavDestination

const val DEFAULT_TITLE = "Enter the Title"
const val DEFAULT_DESCRIPTION = "Add some description"

@Composable
fun TaskScreen(
    destinationContent: NavDestination.Task,
    onBack: () -> Unit,
){
    //TODO Make taskId a BsonObjectId instead of a string, figure out how to load the correct task from MongoDB with Id.
    val taskId = destinationContent.dbLoadObject

    val dummyPlaceholder: ToDoTask = ToDoTask()
    val task = dummyPlaceholder
    task.title = taskId ?: DEFAULT_TITLE

    val viewModel = getViewModel<TaskViewModel>()
    var currentTitle by remember{
        mutableStateOf(task?.title ?: DEFAULT_TITLE)
    }
    var currentDescription by remember{
        mutableStateOf(task?.description ?: DEFAULT_DESCRIPTION)
    }

    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    BasicTextField(
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        ),
                        singleLine = true,
                        value = currentTitle,
                        onValueChange = { currentTitle = it}
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBack.invoke() }
                    ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (currentTitle.isNotEmpty() && currentDescription.isNotEmpty()){
                FloatingActionButton(
                    onClick = {
                        if (task != null) {
                            viewModel.setAction(
                                action = TaskAction.Update(
                                    ToDoTask().apply {
                                        _id = task._id
                                        title = currentTitle
                                        description = currentDescription
                                    }
                                )
                            )
                        } else {
                            viewModel.setAction(
                                action = TaskAction.Add(
                                    ToDoTask().apply {
                                        title = currentTitle
                                        description = currentDescription
                                    }
                                )
                            )
                        }
                        onBack.invoke()
                    },
                    shape = RoundedCornerShape(size = 12.dp)
                ){
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Checkmark Icon"
                    )
                }
            }
        }
    ) { padding ->
        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 24.dp)
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            ),
            value = currentDescription,
            onValueChange = { description -> currentDescription = description}
        )
    }
}
