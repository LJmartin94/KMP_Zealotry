package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.quaver

@OptIn(ExperimentalResourceApi::class)
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
		typography = Typography(defaultFontFamily =
			FontFamily(
					Font(
						Res.font.quaver
					)
			)
		),
		shapes = Shapes()
	) {
		content()
	}
}