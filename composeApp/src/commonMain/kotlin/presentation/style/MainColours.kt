package presentation.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//Light Theme (default):
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
        primary = Color(0xFFBB86FC), //Purple
        primaryVariant = Color(0xFF3700B3), //Dark Purple
        secondary = Color(0xFF03DAC6), //Cyan
        secondaryVariant = Color(0xFF03DAC6), //Cyan
        background = Color(0xFF121212), //Darker Charcoal
        surface = Color(0xFF121212), //Darker Charcoal
        error = Color(0xFFCF6679), //MutedRed
        onPrimary = Color.Black,
        onSecondary = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White,
        onError = Color.Black
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

/* Default light:
*     primary: Color = Color(0xFF6200EE),
    primaryVariant: Color = Color(0xFF3700B3),
    secondary: Color = Color(0xFF03DAC6),
    secondaryVariant: Color = Color(0xFF018786),
    background: Color = Color.White,
    surface: Color = Color.White,
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
*
*/
