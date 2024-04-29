package presentation.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MainColours() =
    if (isSystemInDarkTheme()) {
        darkColors()
    } else {
        lightColors(
            primary = Color(0xFFAFADAA),
            primaryVariant = Color(0xFFCFCECC),
            secondary = Color(0xFFFF9201),
            secondaryVariant = Color(0xFFFF9D1A),
            background = Color(0xFFECEBEA),
            surface = Color(0xFFECEBEA),
            error = Color(0xFFFF1201),
            onPrimary = Color(0xFF262626),
            onSecondary = Color(0xFFFAFAFA),
            onBackground = Color(0xFF262626),
            onSurface = Color(0xFF262626),
            onError = Color(0xFFFAFAFA),
        )
    }
