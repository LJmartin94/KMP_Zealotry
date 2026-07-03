package presentation.mainMenu

import domain.FestiveDay
import domain.Season
import kotlinx.datetime.DayOfWeek
import toad.ViewEvent
import toad.ViewState

data class MainMenuUiState(
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val festiveDay: FestiveDay? = null,
    val dayOfSeason: Int = 0,
    val currentSeason: Season = Season.WINTER,
) : ViewState

sealed interface MainMenuEvent : ViewEvent
