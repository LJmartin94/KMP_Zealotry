package presentation.screens.mainMenu

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.calendar.Season
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.calendar.CalendarFragment
import presentation.calendar.CalendarViewModel
import presentation.resourceComposition.toDrawableResource
import presentation.resourceComposition.toResourceString
import presentation.reusableUi.ImageButton
import presentation.reusableUi.OutlinedText
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.autumn
import zealotry.composeapp.generated.resources.winter
import zealotry.composeapp.generated.resources.day
import zealotry.composeapp.generated.resources.daily_rituals
import zealotry.composeapp.generated.resources.day_button
import zealotry.composeapp.generated.resources.evening
import zealotry.composeapp.generated.resources.evening_button
import zealotry.composeapp.generated.resources.morning
import zealotry.composeapp.generated.resources.morning_button
import zealotry.composeapp.generated.resources.spring
import zealotry.composeapp.generated.resources.summer

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainMenuScreen(
    viewModel: MainMenuViewModel,
    calendarViewModel: CalendarViewModel,
    onNavigate: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val calendar by calendarViewModel.calendarState.collectAsState()

    Box(modifier = with(Modifier) {
        fillMaxSize().paint(
            painterResource(calendar.seasonInfo.currentSeason.toDrawableResource()),
            contentScale = ContentScale.Crop
        )
    })

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.padding(20.dp).weight(1f)) {
            OutlinedText(
                //TODO: tidy up this title
                text = "${calendar.dayOfWeek.toResourceString()}, ${calendar.seasonInfo.dayOfTheSeason} of ${calendar.seasonInfo.currentSeason.toResourceString()}",
                textBorderColour = Color(0xFFFFFFFF),
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

        MainMenuSub(
            title = Res.string.daily_rituals,
            modifier = Modifier.weight(3f)
        ) {

            ImageButton(
                imgModifier = Modifier.fillMaxWidth().weight(1f),
                imgRes = Res.drawable.morning_button,
                imgAlpha = 0.8f,
                onClick = { onNavigate("Morning") },
                textRes = Res.string.morning,
            )

            ImageButton(
                imgModifier = Modifier.fillMaxWidth().weight(1f),
                imgRes = Res.drawable.day_button,
                imgAlpha = 0.8f,
                onClick = { onNavigate("Day") },
                textRes = Res.string.day,
            )

            ImageButton(
                imgModifier = Modifier.fillMaxWidth().weight(1f),
                imgRes = Res.drawable.evening_button,
                imgAlpha = 0.8f,
                onClick = { onNavigate("Evening") },
                textRes = Res.string.evening,
            )

        }
    }
}