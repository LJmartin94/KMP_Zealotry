package z.screens.dayPartMenu

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.empty

@Serializable
enum class DayPart {
    MORNING,
    MIDDAY,
    EVENING,
}

data class TaskButtonState(
    val id: String = "",
    // var state: OrderedMap<String, ChecklistButtonState> = OrderedMap()
)

@OptIn(ExperimentalResourceApi::class)
data class DayPartMenuUIState(
    val part: DayPart = DayPart.MORNING,
    val greeting: StringResource = Res.string.empty,
    var taskButtons: TaskButtonState = TaskButtonState(),
)
