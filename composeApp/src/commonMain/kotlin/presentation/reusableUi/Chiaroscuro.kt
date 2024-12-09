package presentation.reusableUi

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.style.DarkThemeCompositionLocal

@OptIn(ExperimentalResourceApi::class)
class Chiaroscuro(
    private val lightRes: DrawableResource,
    private val darkRes: DrawableResource,
) {
    @Composable
    fun getDrawable(): DrawableResource {
        return if (DarkThemeCompositionLocal.current) this.darkRes else this.lightRes
    }

    @Composable
    fun getPainter(): Painter {
        return painterResource(this.getDrawable())
    }
}
