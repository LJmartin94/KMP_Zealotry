package presentation.calendar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.timeUtils.toCustomString

@Composable
fun CalendarFragment(viewModel: CalendarViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.tryGetToday()
    }

    Text(
        text = uiState.today.toCustomString(),
        modifier = Modifier.padding(20.dp),
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}