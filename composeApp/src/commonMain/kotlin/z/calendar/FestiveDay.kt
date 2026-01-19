package z.calendar

enum class FestiveDay {
    SPRING_START,
    MID_SPRING,
    SUMMER_START,
    MID_SUMMER,
    AUTUMN_START,
    MID_AUTUMN,
    WINTER_START,
    MID_WINTER,
}

fun SeasonInfo.getFestiveDay(): FestiveDay? {
    return when (dayOfTheYear) {
        startOfSpring -> FestiveDay.SPRING_START
        vernalEquinox -> FestiveDay.MID_SPRING
        startOfSummer -> FestiveDay.SUMMER_START
        summerSolstice -> FestiveDay.MID_SUMMER
        startOfAutumn -> FestiveDay.AUTUMN_START
        autumnalEquinox -> FestiveDay.MID_AUTUMN
        startOfWinter -> FestiveDay.WINTER_START
        winterSolstice -> FestiveDay.MID_WINTER
        else -> null
    }
}
