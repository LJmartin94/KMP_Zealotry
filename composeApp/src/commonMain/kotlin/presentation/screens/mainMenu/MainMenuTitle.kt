package presentation.screens.mainMenu

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.screens.mainMenu.MainMenuUIState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.resourceComposition.toOrdinalNumberString
import presentation.resourceComposition.toResourceString
import presentation.reusableUi.OutlinedText
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.main_menu_title

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainMenuTitle(uiState: MainMenuUIState) {
    val dayName = uiState.festiveDay?.toResourceString() ?: uiState.dayOfWeek.toResourceString()
    val ordinalDayOfSeason = uiState.dayOfSeason.toOrdinalNumberString()
    val titleText = stringResource(
        Res.string.main_menu_title,
        dayName,
        ordinalDayOfSeason,
        uiState.currentSeason.toResourceString()
    )
    return OutlinedText(
        text = titleText,
        textBorderColour = Color(0xFFFFFFFF),
        modifier = Modifier.padding(20.dp),
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}