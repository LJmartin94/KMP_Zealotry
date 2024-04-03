package presentation.screens.mainMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.winter
import zealotry.composeapp.generated.resources.day
import zealotry.composeapp.generated.resources.day_button
import zealotry.composeapp.generated.resources.evening
import zealotry.composeapp.generated.resources.evening_button
import zealotry.composeapp.generated.resources.morning
import zealotry.composeapp.generated.resources.morning_button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainMenuScreen(viewModel: MainMenuViewModel, onNavigate: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = with(Modifier) {
        fillMaxSize().paint(
            painterResource(Res.drawable.winter),
            contentScale = ContentScale.Crop
        )
    })

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Placeholder MainMenu",
            modifier = Modifier.padding(20.dp).weight(1f),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Column(
            Modifier.weight(3f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MainMenuButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                navOp = { onNavigate("Morning") },
                textRes = Res.string.morning,
                backgroundRes = Res.drawable.morning_button
            )

            MainMenuButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                navOp = { onNavigate("Day") },
                textRes = Res.string.day,
                backgroundRes = Res.drawable.day_button
            )

            MainMenuButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                navOp = { onNavigate("Evening") },
                textRes = Res.string.evening,
                backgroundRes = Res.drawable.evening_button
            )

        }
    }
}