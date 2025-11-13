package domain.screens.dayPartMenu

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import libs.dataStructures.OrderedMap
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.mongodb.kbson.ObjectId
import presentation.screens.dayPartMenu.checklistButtons.ChecklistButtonState
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.empty

enum class DayPart {
    MORNING,
    MIDDAY,
    EVENING,
}

class TaskButtonState : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    val state: OrderedMap<String, ChecklistButtonState> = OrderedMap()
}

@OptIn(ExperimentalResourceApi::class)
data class DayPartMenuUIState(
    val part: DayPart = DayPart.MORNING,
    val greeting: StringResource = Res.string.empty,
    var taskButtons: TaskButtonState = TaskButtonState(),
)
