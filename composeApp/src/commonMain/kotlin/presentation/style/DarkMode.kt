package presentation.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import presentation.reusableUi.greyScale

val DarkThemeCompositionLocal =
    compositionLocalOf<Boolean> {
        false
    }

@Composable
fun isDark(): Boolean = isSystemInDarkTheme()

@Composable
@Suppress("MagicNumber")
fun Modifier.dimIfDarkMode(): Modifier =
    when (DarkThemeCompositionLocal.current) {
        true -> greyScale(0.77f)
        else -> this
    }
