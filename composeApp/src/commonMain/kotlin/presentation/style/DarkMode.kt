package presentation.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import presentation.reusableUi.greyScale

val DarkThemeCompositionLocal =
    compositionLocalOf<Boolean> {
        error("Theme not set")
    }

@Composable
fun isDark(): Boolean {
    return isSystemInDarkTheme()
}

@Composable
@Suppress("MagicNumber")
fun Modifier.setIfDarkMode(): Modifier =
    when (DarkThemeCompositionLocal.current) {
        true -> greyScale(0.77f)
        else -> this
    }
