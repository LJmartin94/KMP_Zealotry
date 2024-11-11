package presentation.screens.dayPartMenu

import androidx.lifecycle.ViewModel
import data.screens.dayPartMenu.DayPart
import data.screens.dayPartMenu.DayPartMenuUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.good_day
import zealotry.composeapp.generated.resources.good_evening
import zealotry.composeapp.generated.resources.good_morning

class DayPartMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DayPartMenuUIState())
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalResourceApi::class)
    fun setDayPart(part: DayPart) {
        _uiState.update { currentState ->
            currentState.copy(
                part = part,
                greeting = setGreeting(part),
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun setGreeting(part: DayPart): StringResource =
        when (part) {
            DayPart.MORNING -> Res.string.good_morning
            DayPart.MIDDAY -> Res.string.good_day
            DayPart.EVENING -> Res.string.good_evening
        }
}
