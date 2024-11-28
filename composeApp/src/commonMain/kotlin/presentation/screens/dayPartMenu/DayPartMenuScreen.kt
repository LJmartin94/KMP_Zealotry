package presentation.screens.dayPartMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.screens.dayPartMenu.DayPart
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
    val visibleButtons =
        when (uiState.part) {
            DayPart.MORNING -> MorningButtons.entries
            DayPart.MIDDAY -> emptyList() // Placeholder
            DayPart.EVENING -> emptyList()
        }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(uiState.greeting),
            modifier = Modifier.padding(20.dp).wrapContentSize(),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = visibleButtons) {
                    button ->
                IconTextTimeButton(button.toBundle())
            }
        }
    }
}
