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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import libs.localisation.getLocale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import presentation.resourceComposition.toDrawableResource
import presentation.reusableUi.ImageButton
import presentation.reusableUi.OutlinedText
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.daily_rituals
import zealotry.composeapp.generated.resources.day
import zealotry.composeapp.generated.resources.day_button
import zealotry.composeapp.generated.resources.evening
import zealotry.composeapp.generated.resources.evening_button
import zealotry.composeapp.generated.resources.morning
import zealotry.composeapp.generated.resources.morning_button
import zealotry.composeapp.generated.resources.ordinal


@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainMenuScreen(
    viewModel: MainMenuViewModel,
    onNavigate: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = with(Modifier) {
        fillMaxSize().paint(
            painterResource(uiState.backgroundSeason.toDrawableResource()),
            contentScale = ContentScale.Crop
        )
    })
    val a = Locale.current.language
    val b = LocaleList.current.localeList.toString()


    val testInt: Int = 12
    val pluralForm = getLocale(Locale.current.language).getOrdinal(testInt)
    val suffix = stringArrayResource(Res.string.ordinal)[pluralForm.ordinal]
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.padding(20.dp).weight(1f)) {
            OutlinedText(
                //TODO: tidy up this title
                text = testInt.toString() + suffix,
//                text= a+stringArrayResource(Res.string.ordinal)[2],
//                text = "1 2 3 4 8 10 11 12 13 14 20 21 22 23 24 100",
                //text = "${calendar.dayOfWeek.toResourceString()}, ${calendar.seasonInfo.dayOfTheSeason} of ${calendar.seasonInfo.currentSeason.toResourceString()}",
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