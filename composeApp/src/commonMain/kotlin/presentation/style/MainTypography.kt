package presentation.style

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.Font
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.quaver

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainTypography() = Typography(defaultFontFamily =
    FontFamily(
        Font(Res.font.quaver)
    )
)