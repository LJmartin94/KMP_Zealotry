package presentation.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.reusableUi.greyScale

@Composable
fun Modifier.setIfDarkMode() : Modifier = when(isSystemInDarkTheme()) {
    true -> greyScale(0.9f)
    else -> this
}