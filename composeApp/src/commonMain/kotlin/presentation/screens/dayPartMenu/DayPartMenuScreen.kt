package presentation.screens.dayPartMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import org.jetbrains.compose.resources.stringResource
import presentation.reusableUi.IconTextTimeButton
import presentation.screens.dayPartMenu.morningButtons.MorningButtons
import presentation.screens.dayPartMenu.morningButtons.toBundle

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DayPartMenuScreen(
    viewModel: DayPartMenuViewModel,
    onNavigate: (() -> Unit)? = null,
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(all = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(1) { //TODO: Make this less hacky //https://stackoverflow.com/questions/78599110/lazycolumn-and-error-composable-invocations-can-only-happen-from-the-context-of
            Text(
                text = stringResource(uiState.greeting),
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )

            for (button in MorningButtons.entries) {
                IconTextTimeButton(button.toBundle())
            }
        }
    }
}


