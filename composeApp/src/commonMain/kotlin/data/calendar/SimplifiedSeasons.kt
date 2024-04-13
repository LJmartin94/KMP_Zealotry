package data.calendar

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

enum class Season {
    WINTER, SPRING, SUMMER, AUTUMN
}

data class SeasonInfo(
    val dayOfTheYear: Int = 0,
    val vernalEquinox: Int = 0,
    val summerSolstice: Int = 0,
    val autumnalEquinox: Int = 0,
    val winterSolstice: Int = 0,
    val startOfSpring: Int = 0,
    val startOfSummer: Int = 0,
    val startOfAutumn: Int = 0,
    val startOfWinter: Int = 0,
    val dayOfTheSeason: Int = 0,
    val currentSeason: Season = Season.WINTER
)

fun getSeasonInfo(
    moment: Instant,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): SeasonInfo {
    val localTime = moment.toLocalDateTime(timeZone) //TODO: Make this tick over at 4am
    val dayOfTheYear = localTime.dayOfYear
    val utcTime = moment.toLocalDateTime(TimeZone.UTC)

    //Reference Equinoxes and Solstices for 2005, UTC
    var refYear = 2005
    var refSpringEqi: Instant = Instant.parse("2005-03-20T11:33:19Z")
    var refSummerSol: Instant = Instant.parse("2005-06-21T06:39:11Z")
    var refAutumnEqi: Instant = Instant.parse("2005-09-22T22:16:34Z")
    var refWinterSol: Instant = Instant.parse("2005-12-21T18:34:51Z")

    //Constants to add to reference solstice/equinoxes for approximation of subsequent S & Es
    val springConstant: Duration = Duration.parseIsoString("P365DT5H49M2S")
    val summerConstant: Duration = Duration.parseIsoString("P365DT5H47M57S")
    val autumnConstant: Duration = Duration.parseIsoString("P365DT5H48M31S")
    val winterConstant: Duration = Duration.parseIsoString("P365DT5H49M33S")

    //Increment until desired year is reached - accurate enough for getting the right day
    val utcYear = utcTime.year
    while (refYear < utcYear) {
        refYear += 1
        refSpringEqi += springConstant
        refSummerSol += summerConstant
        refAutumnEqi += autumnConstant
        refWinterSol += winterConstant
//        println("Year: $refYear")
//        println("Vernal Equinox: $refSpringEqi")
//        println("Summer Solstice: $refSummerSol")
//        println("Autumn Equinox: $refAutumnEqi")
//        println("Winter Solstice: $refWinterSol")
    }

    //Define days of equinoxes and solstices this year
    val vernalEquinox = refSpringEqi.toLocalDateTime(TimeZone.UTC).dayOfYear
    val summerSolstice = refSummerSol.toLocalDateTime(TimeZone.UTC).dayOfYear
    val autumnalEquinox = refAutumnEqi.toLocalDateTime(TimeZone.UTC).dayOfYear
    val winterSolstice = refWinterSol.toLocalDateTime(TimeZone.UTC).dayOfYear

    //Additional calculations for start of spring, counting from previous year
    val prevWinterSolstice = (refWinterSol - winterConstant)
    val untilMidSpring = prevWinterSolstice.daysUntil(refSpringEqi, TimeZone.UTC)
    val untilStartSpring = untilMidSpring / 2
    val springStartDate = prevWinterSolstice + Duration.parseIsoString("P${untilStartSpring}D")

    //Define start of seasons as midpoints between Solstice & Equinox
    val startOfSpring = springStartDate.toLocalDateTime(TimeZone.UTC).dayOfYear
    val startOfSummer = (vernalEquinox + summerSolstice) / 2
    val startOfAutumn = (summerSolstice + autumnalEquinox) / 2
    val startOfWinter = (autumnalEquinox + winterSolstice) / 2

    //Get current season based on start dates
    val currentSeason = when {
        dayOfTheYear < startOfSpring -> Season.WINTER
        dayOfTheYear < startOfSummer -> Season.SPRING
        dayOfTheYear < startOfAutumn -> Season.SUMMER
        dayOfTheYear < startOfWinter -> Season.AUTUMN
        else -> Season.WINTER
    }

    //Additional calculations for number of days in Winter from previous year, before spring
    val prevAutumnalEquinox = (refAutumnEqi - autumnConstant)
    val untilMidWinter = prevAutumnalEquinox.daysUntil(prevWinterSolstice, TimeZone.UTC)
    val untilStartWinter = untilMidWinter / 2
    val prevWinterStartDate = prevAutumnalEquinox + Duration.parseIsoString("P${untilStartWinter}D")
    val winterDuration = prevWinterStartDate.daysUntil(springStartDate, TimeZone.UTC)

    //Calculate how many days into the season we are
    val dayOfTheSeason = when (currentSeason) {
        Season.SPRING -> dayOfTheYear - startOfSpring + 1
        Season.SUMMER -> dayOfTheYear - startOfSummer + 1
        Season.AUTUMN -> dayOfTheYear - startOfAutumn + 1
        else ->
            if (dayOfTheYear > startOfWinter) dayOfTheYear - startOfWinter + 1 else
                winterDuration - (startOfSpring - dayOfTheYear) + 1
    }

    return SeasonInfo(
        dayOfTheYear = dayOfTheYear,
        vernalEquinox = vernalEquinox,
        summerSolstice = summerSolstice,
        autumnalEquinox = autumnalEquinox,
        winterSolstice = winterSolstice,
        startOfSpring = startOfSpring,
        startOfSummer = startOfSummer,
        startOfAutumn = startOfAutumn,
        startOfWinter = startOfWinter,
        dayOfTheSeason = dayOfTheSeason,
        currentSeason = currentSeason,
    )
}