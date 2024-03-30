package presentation.screens.mainMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(viewModel: MainMenuViewModel, onNavigate: (String)->Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = "Placeholder MorningMenu",
            modifier = Modifier.padding(20.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        OutlinedButton(onClick = {onNavigate("Morning")}){ Text("Morning") }
        OutlinedButton(onClick = {onNavigate("Day")}){ Text("Day") }
        OutlinedButton(onClick = {onNavigate("Evening")}){ Text("Evening") }
    }
}