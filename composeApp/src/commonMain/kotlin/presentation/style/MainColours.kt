package presentation.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colour constants:
val BROWN_ORANGE = Color(0xFFEB9626)
val DARK_SAND = Color(0xFFF0AD54)
val DARKEST_CHARCOAL = Color(0xFF121212)
val MUTED_RED = Color(0xFFCF6679)
val CHARCOAL = Color(0xFF262626)
val DARK_GREY = Color(0xFFAFADAA)
val GREY = Color(0xFFCFCECC)
val PALE_GREY = Color(0xFFECEBEA)
val WHITE_ISH = Color(0xFFFAFAFA)
val DARK_ORANGE = Color(0xFFFF9201)
val ORANGE = Color(0xFFFF9D1A)
val RED = Color(0xFFFF1201)

@Composable
fun MainColours() =
    if (isSystemInDarkTheme()) {
        darkColors(
            primary = CHARCOAL,
            primaryVariant = DARKEST_CHARCOAL,
            secondary = BROWN_ORANGE,
            secondaryVariant = DARK_SAND,
            background = DARK_GREY,
            surface = DARK_GREY,
            error = MUTED_RED,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = CHARCOAL,
            onSurface = CHARCOAL,
            onError = Color.Black,
        )
    } else {
        lightColors(
            primary = DARK_GREY,
            primaryVariant = GREY,
            secondary = DARK_ORANGE,
            secondaryVariant = ORANGE,
            background = PALE_GREY,
            surface = PALE_GREY,
            error = RED,
            onPrimary = CHARCOAL,
            onSecondary = WHITE_ISH,
            onBackground = CHARCOAL,
            onSurface = CHARCOAL,
            onError = WHITE_ISH,
        )
    }
