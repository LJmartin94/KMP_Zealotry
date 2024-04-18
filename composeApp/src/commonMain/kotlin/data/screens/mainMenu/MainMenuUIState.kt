package data.screens.mainMenu

import data.calendar.Season
import kotlinx.datetime.DayOfWeek

data class MainMenuUIState(
    val dayName : DayOfWeek = DayOfWeek.MONDAY,
    val backgroundSeason: Season = Season.WINTER
)