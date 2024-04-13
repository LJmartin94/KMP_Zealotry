package data.calendar

enum class Season{
    WINTER, SPRING, SUMMER, AUTUMN
}

data class SeasonInfo(
    val dayOfTheYear: Int = 0,
    val startOfSpring: Int = 0,
    val vernalEquinox: Int = 0,
    val startOfSummer: Int = 0,
    val summerSolstice: Int = 0,
    val startOfAutumn: Int = 0,
    val autumnalEquinox: Int = 0,
    val startOfWinter: Int = 0,
    val winterSolstice: Int = 0,
    val dayOfTheSeason: Int = 0,
    val currentSeason: Season = Season.WINTER
)