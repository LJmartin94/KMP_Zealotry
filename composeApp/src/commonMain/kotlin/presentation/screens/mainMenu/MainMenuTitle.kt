package presentation.screens.mainMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import z.screens.mainMenu.MainMenuUIState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.resourceComposition.toOrdinalNumberString
import presentation.resourceComposition.toResourceString
import presentation.reusableUi.OutlinedText
import presentation.style.MainTypography
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.day_of_season

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainMenuTitle(
    uiState: MainMenuUIState,
    modifier: Modifier = Modifier,
) {
    val dayName = uiState.festiveDay?.toResourceString() ?: uiState.dayOfWeek.toResourceString()
    val dayXOfSeasonY =
        stringResource(
            Res.string.day_of_season,
            uiState.dayOfSeason.toOrdinalNumberString(),
            uiState.currentSeason.toResourceString(),
        )
    return Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedText(
            text = dayXOfSeasonY,
            textAlign = TextAlign.Center,
            style = MainTypography().h5,
        )
        OutlinedText(
            text = dayName,
            textAlign = TextAlign.Center,
            style = MainTypography().h4,
        )
    }
}
