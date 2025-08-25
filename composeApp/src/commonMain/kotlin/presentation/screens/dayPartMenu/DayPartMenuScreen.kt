package presentation.screens.dayPartMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.screens.dayPartMenu.DayPart
import libs.mvvm.getViewModel
import navigation.NavDestination
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.screens.dayPartMenu.checklistButtons.ChecklistButton
import presentation.screens.dayPartMenu.morningButtons.MorningButtons

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DayPartMenuScreen(
    content: NavDestination.DayPart,
    onBack: () -> Unit,
) {
    val viewModel = getViewModel<DayPartMenuViewModel>()
    viewModel.setDayPart(content.part)
    val uiState by viewModel.uiState.collectAsState()
    val visibleButtons = // uiState.taskButtons.
        when (uiState.part) {
            DayPart.MORNING -> MorningButtons.entries
            DayPart.MIDDAY -> emptyList() // Placeholder
            DayPart.EVENING -> emptyList()
        }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(uiState.greeting),
            modifier = Modifier.padding(20.dp).wrapContentSize(),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            visibleButtons.forEach { button -> ChecklistButton(button) }
        }
    }
}
