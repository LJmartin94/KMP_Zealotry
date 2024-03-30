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

@Composable
fun DayPartMenuScreen(viewModel: DayPartMenuViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Text(
        text = "Placeholder DayPartMenu",
        modifier = Modifier.padding(20.dp),
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}