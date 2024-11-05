package data.screens.dayPartMenu

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.empty

enum class DayPart {
    MORNING,
    MIDDAY,
    EVENING,
}

@OptIn(ExperimentalResourceApi::class)
data class DayPartMenuUIState(
    val part: DayPart = DayPart.MORNING,
    val greeting: StringResource = Res.string.empty,
)
