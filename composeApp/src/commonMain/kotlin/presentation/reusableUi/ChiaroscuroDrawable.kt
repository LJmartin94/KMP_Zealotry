package presentation.reusableUi

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.style.DarkThemeCompositionLocal

@OptIn(ExperimentalResourceApi::class)
class ChiaroscuroDrawable(
    private val lightRes: DrawableResource,
    private val darkRes: DrawableResource,
) {
    @Composable
    fun getDrawable(): DrawableResource {
        return if (DarkThemeCompositionLocal.current) this.darkRes else this.lightRes
    }
}
