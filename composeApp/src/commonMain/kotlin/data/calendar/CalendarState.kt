package data.calendar

import kotlinx.datetime.DayOfWeek

data class CalendarState (
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val dayOfTheYear: Int = 0,
    val startOfSpring: Int = 0,
    val vernalEquinox: Int = 0,
    val startOfSummer: Int = 0,
    val summerSolstice: Int = 0,
    val startOfAutumn: Int = 0,
    val autumnalEquinox: Int = 0,
    val startOfWinter: Int = 0,
    val WinterSolstice: Int = 0,
    val dayOfTheSeason: Int = 0
)