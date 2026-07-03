package presentation.dayPartMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.dayPartMenu.DayPart
import navigation.NavDestination
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.good_day
import zealotry.composeapp.generated.resources.good_evening
import zealotry.composeapp.generated.resources.good_morning
import presentation.dayPartMenu.checklistButtons.ChecklistButton
import presentation.dayPartMenu.morningButtons.MorningButtons
import presentation.example.ExampleScreen
import toad.getViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DayPartMenuScreen(
    content: NavDestination.DayPart,
    onBack: () -> Unit,
) {
    val viewModel = getViewModel<DayPartMenuViewModel>()
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(content.part) {
        viewModel.runAction(SetDayPart(content.part))
    }
    val greeting = when (uiState.part) {
        DayPart.MORNING -> stringResource(Res.string.good_morning)
        DayPart.MIDDAY -> stringResource(Res.string.good_day)
        DayPart.EVENING -> stringResource(Res.string.good_evening)
    }
    val visibleButtons =
        when (uiState.part) {
            DayPart.MORNING -> MorningButtons.entries
            DayPart.MIDDAY -> emptyList() // Placeholder
            DayPart.EVENING -> emptyList()
        }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = greeting,
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

        if (uiState.part == DayPart.MIDDAY) {
            ExampleScreen()
        }
    }
}
