package presentation.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colour constants:
const val BROWN_ORANGE = 0xFFEB9626
const val DARK_SAND = 0xFFF0AD54
const val DARKEST_CHARCOAL = 0xFF121212
const val MUTED_RED = 0xFFCF6679
const val CHARCOAL = 0xFF262626
const val DARK_GREY = 0xFFAFADAA
const val GREY = 0xFFCFCECC
const val PALE_GREY = 0xFFECEBEA
const val WHITE_ISH = 0xFFFAFAFA
const val DARK_ORANGE = 0xFFFF9201
const val ORANGE = 0xFFFF9D1A
const val RED = 0xFFFF1201

@Composable
fun MainColours() =
    if (isSystemInDarkTheme()) {
        darkColors(
            primary = Color(DARK_GREY),
            primaryVariant = Color(CHARCOAL),
            onPrimary = Color(WHITE_ISH),
            secondary = Color(BROWN_ORANGE),
            secondaryVariant = Color(DARK_SAND),
            onSecondary = Color.White,
            background = Color(DARKEST_CHARCOAL),
            onBackground = Color(WHITE_ISH),
            surface = Color(DARKEST_CHARCOAL),
            onSurface = Color(WHITE_ISH),
            error = Color(MUTED_RED),
            onError = Color.Black,
        )
    } else {
        lightColors(
            primary = Color(DARK_GREY),
            primaryVariant = Color(GREY),
            onPrimary = Color(CHARCOAL),
            secondary = Color(DARK_ORANGE),
            secondaryVariant = Color(ORANGE),
            onSecondary = Color(WHITE_ISH),
            background = Color(PALE_GREY),
            onBackground = Color(CHARCOAL),
            surface = Color(PALE_GREY),
            onSurface = Color(CHARCOAL),
            error = Color(RED),
            onError = Color(WHITE_ISH),
        )
    }
