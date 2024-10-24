package presentation.screens.dayPartMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.reusableUi.IconTextTimeButton
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.day
import zealotry.composeapp.generated.resources.wake_up

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DayPartMenuScreen(
    viewModel: DayPartMenuViewModel,
    onNavigate: (() -> Unit)? = null,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Placeholder DayPartMenu ${uiState.part.name}",
            modifier = Modifier.padding(20.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        IconTextTimeButton(
            iconRes = Res.drawable.wake_up,
            textRes = Res.string.day,
            timeRes = Res.string.day,
        ) {onNavigate?.invoke()}
    }
}
