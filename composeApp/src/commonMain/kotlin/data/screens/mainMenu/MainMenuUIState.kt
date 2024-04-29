package data.screens.mainMenu

import data.calendar.FestiveDay
import data.calendar.Season
import kotlinx.datetime.DayOfWeek

data class MainMenuUIState(
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val festiveDay: FestiveDay? = null,
    val dayOfSeason: Int = 0,
    val currentSeason: Season = Season.WINTER,
)
