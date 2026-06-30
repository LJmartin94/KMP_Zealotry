package z.screens.mainMenu

import kotlinx.datetime.DayOfWeek
import z.calendar.FestiveDay
import z.calendar.Season

data class MainMenuUIState(
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val festiveDay: FestiveDay? = null,
    val dayOfSeason: Int = 0,
    val currentSeason: Season = Season.WINTER,
)
