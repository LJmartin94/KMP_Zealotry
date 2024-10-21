package presentation.screens.dayPartMenu

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.screens.dayPartMenu.DayPart
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.reusableUi.ImageButton
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.day_button
import zealotry.composeapp.generated.resources.evening_button
import zealotry.composeapp.generated.resources.morning
import zealotry.composeapp.generated.resources.morning_button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DayPartMenuScreen(
    viewModel: DayPartMenuViewModel,
    onNavigate: (() -> Unit)? = null,
) {
    val uiState by viewModel.uiState.collectAsState()

    Text(
        text = "Placeholder DayPartMenu ${uiState.part.name}",
        modifier = Modifier.padding(20.dp),
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
    )
    ImageButton(
        imgRes = tempGetImg(uiState.part),
        imgAlpha = 0.8f,
        onClick = { onNavigate?.invoke() },
        textRes = Res.string.morning,
    )
}

@OptIn(ExperimentalResourceApi::class)
fun tempGetImg(part: DayPart): DrawableResource =
    when (part) {
        DayPart.MORNING -> Res.drawable.morning_button
        DayPart.MIDDAY -> Res.drawable.day_button
        DayPart.EVENING -> Res.drawable.evening_button
    }
