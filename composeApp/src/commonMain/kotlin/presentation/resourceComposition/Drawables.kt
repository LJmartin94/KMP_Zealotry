package presentation.resourceComposition

import androidx.compose.runtime.Composable
import data.calendar.Season
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.autumn
import zealotry.composeapp.generated.resources.spring
import zealotry.composeapp.generated.resources.summer
import zealotry.composeapp.generated.resources.winter

@Composable
@OptIn(ExperimentalResourceApi::class)
fun Season.toDrawableResource(): DrawableResource  {
    return when (this) {
        Season.SPRING -> Res.drawable.spring
        Season.SUMMER -> Res.drawable.summer
        Season.AUTUMN -> Res.drawable.autumn
        Season.WINTER -> Res.drawable.winter
    }
}
