package presentation.style

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun MainTheme(content: @Composable () -> Unit) {
    val colours = MainColours()
    val typography = MainTypography()
    val shapes = Shapes()
    val dpConf = rememberWindowSize().dpConf()

    MaterialTheme(
        colors = colours,
        typography = typography,
        shapes = shapes,
    ) {
        CompositionLocalProvider(
            DpCompositionLocal provides dpConf,
        ) {
            content()
        }
    }
}

object Theme {
    val colour: Colors
        @Composable @ReadOnlyComposable
        get() = MaterialTheme.colors

    val typo: Typography
        @Composable @ReadOnlyComposable
        get() = MaterialTheme.typography

    val dp: DpConf
        @Composable @ReadOnlyComposable
        get() = DpCompositionLocal.current
}
