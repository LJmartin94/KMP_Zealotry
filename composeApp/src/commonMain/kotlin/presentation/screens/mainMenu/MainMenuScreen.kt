package presentation.screens.mainMenu

import navigation.Screen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.resourceComposition.toDrawableResource
import presentation.reusableUi.ImageButton
import presentation.style.COMPONENT_EQUAL_WEIGHT
import presentation.style.EMPTY_SPACE
import presentation.style.FILLER_SPACE
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.daily_rituals
import zealotry.composeapp.generated.resources.day
import zealotry.composeapp.generated.resources.day_button
import zealotry.composeapp.generated.resources.evening
import zealotry.composeapp.generated.resources.evening_button
import zealotry.composeapp.generated.resources.morning
import zealotry.composeapp.generated.resources.morning_button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainMenuScreen(
    viewModel: MainMenuViewModel,
    onNavigate: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier =
            with(Modifier) {
                fillMaxSize().paint(
                    painterResource(uiState.currentSeason.toDrawableResource()),
                    contentScale = ContentScale.Crop,
                )
            },
    )

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MainMenuTitle(
            uiState = uiState,
            modifier = Modifier.padding(20.dp).weight(EMPTY_SPACE),
        )
        MainMenuSub(
            title = Res.string.daily_rituals,
            modifier = Modifier.weight(FILLER_SPACE),
        ) {
            ImageButton(
                imgModifier = Modifier.fillMaxWidth().weight(COMPONENT_EQUAL_WEIGHT),
                imgRes = Res.drawable.morning_button,
                imgAlpha = 0.8f,
                onClick = { onNavigate(Screen.Morning.name) },
                textRes = Res.string.morning,
            )

            ImageButton(
                imgModifier = Modifier.fillMaxWidth().weight(COMPONENT_EQUAL_WEIGHT),
                imgRes = Res.drawable.day_button,
                imgAlpha = 0.8f,
                onClick = { onNavigate(Screen.Day.name) },
                textRes = Res.string.day,
            )

            ImageButton(
                imgModifier = Modifier.fillMaxWidth().weight(COMPONENT_EQUAL_WEIGHT),
                imgRes = Res.drawable.evening_button,
                imgAlpha = 0.8f,
                onClick = { onNavigate(Screen.Evening.name) },
                textRes = Res.string.evening,
            )
        }
    }
}
