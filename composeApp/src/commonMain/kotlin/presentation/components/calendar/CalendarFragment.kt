package presentation.components.calendar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.resourceComposition.toResourceString
import presentation.reusableUi.OutlinedText

@Composable
fun CalendarFragment(viewModel: CalendarViewModel) {
    val uiState by viewModel.calendarState.collectAsState()

    OutlinedText(
        text = uiState.dayOfWeek.toResourceString(),
        modifier = Modifier.padding(20.dp),
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
    )
}
