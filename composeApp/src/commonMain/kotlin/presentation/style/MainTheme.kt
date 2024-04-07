package presentation.style

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val ColourCompositionLocal = compositionLocalOf<Colors> {
    error("No Colour provided")
}

@Composable
fun MainTheme(
    content: @Composable () -> Unit
) {
    val colours = MainColours()
    val typography = MainTypography()
    val shapes = Shapes()

    MaterialTheme(
        colors = colours,
        typography = typography,
        shapes = shapes
    ) {
        CompositionLocalProvider(ColourCompositionLocal provides colours){
            content()
        }
    }
}