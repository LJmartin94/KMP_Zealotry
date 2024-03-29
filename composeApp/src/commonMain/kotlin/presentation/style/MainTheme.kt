package presentation.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun MainTheme(
	content: @Composable () -> Unit
) {
	MaterialTheme (
		colors = if (isSystemInDarkTheme()) {
			darkColors()
		} else {
			lightColors()
		},
		typography = MainTypography(),
		shapes = Shapes()
	) {
		content()
	}
}