package domain

import kotlin.time.Instant
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class GetAstronomicalContextUseCase(
    private val timeZone: TimeZone = TimeZone.currentSystemDefault(),
) {
    operator fun invoke(moment: Instant): AstronomicalContext {
        val effectiveDate = effectiveDateOf(moment, timeZone)
        // Use noon of the effective date so SeasonInfo is never given a midnight or
        // DST-gap instant.
        val referenceInstant = LocalDateTime(effectiveDate, LocalTime(12, 0)).toInstant(timeZone)
        val seasonInfo = SeasonInfo(referenceInstant, timeZone)
        return AstronomicalContext(
            dayOfWeek = effectiveDate.dayOfWeek,
            dayOfSeason = seasonInfo.dayOfTheSeason,
            season = seasonInfo.currentSeason,
            festiveDay = seasonInfo.getFestiveDay(),
        )
    }

    companion object {
        // The app day changes at 4am local time. Before 4am, the previous calendar
        // date is used so late-night moments are treated as part of the previous day.

        fun effectiveDateOf(moment: Instant, timeZone: TimeZone): LocalDate {
            val local = moment.toLocalDateTime(timeZone)
            return if (local.hour < 4) local.date.plus(-1, DateTimeUnit.DAY) else local.date
        }

        fun nextAppDayInstant(moment: Instant, timeZone: TimeZone): Instant {
            val local = moment.toLocalDateTime(timeZone)
            val next4amDate = if (local.hour < 4) local.date else local.date.plus(1, DateTimeUnit.DAY)
            return LocalDateTime(next4amDate, LocalTime(4, 0)).toInstant(timeZone)
        }
    }
}

// ---------------------------------------------------------------------------
// Output type
// ---------------------------------------------------------------------------

data class AstronomicalContext(
    val dayOfWeek: DayOfWeek,
    val dayOfSeason: Int,
    val season: Season,
    val festiveDay: FestiveDay?,
)

// ---------------------------------------------------------------------------
// Domain enums
// ---------------------------------------------------------------------------

enum class Season {
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER,
}

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

// ---------------------------------------------------------------------------
// SeasonInfo — internal implementation detail, not exposed outside this file
// ---------------------------------------------------------------------------

private data class SeasonInfo(
    val moment: Instant,
    val timeZone: TimeZone = TimeZone.currentSystemDefault(),
) {
    private val localTime = moment.toLocalDateTime(timeZone)
    val dayOfTheYear: Int = localTime.dayOfYear
    val date: LocalDate = localTime.date

    private val eqiSol = getEquinoxesAndSolstices(localTime.year)
    val vernalEquinox: Int = eqiSol.getValue(Season.SPRING).toUtcDayOfYear()
    val summerSolstice: Int = eqiSol.getValue(Season.SUMMER).toUtcDayOfYear()
    val autumnalEquinox: Int = eqiSol.getValue(Season.AUTUMN).toUtcDayOfYear()
    val winterSolstice: Int = eqiSol.getValue(Season.WINTER).toUtcDayOfYear()
    private val prevEqiSol = getEquinoxesAndSolstices(localTime.year - 1)

    private val seasonStarts = getSeasonStart(eqiSol, prevEqiSol)
    val startOfSpring: Int = seasonStarts.getValue(Season.SPRING).toUtcDayOfYear()
    val startOfSummer: Int = seasonStarts.getValue(Season.SUMMER).toUtcDayOfYear()
    val startOfAutumn: Int = seasonStarts.getValue(Season.AUTUMN).toUtcDayOfYear()
    val startOfWinter: Int = seasonStarts.getValue(Season.WINTER).toUtcDayOfYear()
    private val prevWinterStart = getSeasonStart(prevEqiSol, prevEqiSol).getValue(Season.WINTER)

    val currentSeason: Season = getCurrentSeason(dayOfTheYear, seasonStarts)
    val dayOfTheSeason: Int =
        getDayOfSeason(currentSeason, dayOfTheYear, seasonStarts, prevWinterStart)

    fun getFestiveDay(): FestiveDay? =
        when (dayOfTheYear) {
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

// ---------------------------------------------------------------------------
// Pure computation functions — internal to this file
// ---------------------------------------------------------------------------

/**
 * Naive and relatively inaccurate method for getting the solstice and equinoxes of a given year
 * after 2005. But will be accurate enough - get the right day.
 *
 * @return Map of Instants on which spring eqi, summer sol, autumn eqi, winter sol occur
 */
private const val REF_YEAR = 2005

private fun getEquinoxesAndSolstices(year: Int): Map<Season, Instant> {
    // Reference Equinoxes and Solstices for 2005 (REF_YEAR), UTC
    var vernalEquinox: Instant = Instant.parse("2005-03-20T11:33:19Z")
    var summerSolstice: Instant = Instant.parse("2005-06-21T06:39:11Z")
    var autumnalEquinox: Instant = Instant.parse("2005-09-22T22:16:34Z")
    var winterSolstice: Instant = Instant.parse("2005-12-21T18:34:51Z")

    // Constants to add to reference solstice/equinoxes for approximation of subsequent S & Es
    val springConstant: Duration = Duration.parseIsoString("P365DT5H49M2S")
    val summerConstant: Duration = Duration.parseIsoString("P365DT5H47M57S")
    val autumnConstant: Duration = Duration.parseIsoString("P365DT5H48M31S")
    val winterConstant: Duration = Duration.parseIsoString("P365DT5H49M33S")

    // Increment until desired year is reached - accurate enough for getting the right day
    val increments = year - REF_YEAR
    vernalEquinox += springConstant * increments
    summerSolstice += summerConstant * increments
    autumnalEquinox += autumnConstant * increments
    winterSolstice += winterConstant * increments
//        println("Year: $refYear")
//        println("Vernal Equinox: $vernalEquinox")
//        println("Summer Solstice: $summerSolstice")
//        println("Autumn Equinox: $autumnalEquinox")
//        println("Winter Solstice: $winterSolstice")

    return mapOf(
        Season.SPRING to vernalEquinox,
        Season.SUMMER to summerSolstice,
        Season.AUTUMN to autumnalEquinox,
        Season.WINTER to winterSolstice,
    )
}

private fun getSeasonStart(
    currentEqiSols: Map<Season, Instant>,
    prevEqiSols: Map<Season, Instant>,
): Map<Season, Instant> {
    val prevWinterSolstice = prevEqiSols.getValue(Season.WINTER)
    val vernalEquinox = currentEqiSols.getValue(Season.SPRING)
    val summerSolstice = currentEqiSols.getValue(Season.SUMMER)
    val autumnalEquinox = currentEqiSols.getValue(Season.AUTUMN)
    val winterSolstice = currentEqiSols.getValue(Season.WINTER)

    // Define start of seasons as midpoints between Solstice & Equinox
    val untilMidSpring = prevWinterSolstice.daysUntil(vernalEquinox, TimeZone.UTC)
    val startOfSpring = prevWinterSolstice + Duration.parseIsoString("P${untilMidSpring / 2}D")

    val untilMidSummer = vernalEquinox.daysUntil(summerSolstice, TimeZone.UTC)
    val startOfSummer = vernalEquinox + Duration.parseIsoString("P${untilMidSummer / 2}D")

    val untilMidAutumn = summerSolstice.daysUntil(autumnalEquinox, TimeZone.UTC)
    val startOfAutumn = summerSolstice + Duration.parseIsoString("P${untilMidAutumn / 2}D")

    val untilMidWinter = autumnalEquinox.daysUntil(winterSolstice, TimeZone.UTC)
    val startOfWinter = autumnalEquinox + Duration.parseIsoString("P${untilMidWinter / 2}D")

    return mapOf(
        Season.SPRING to startOfSpring,
        Season.SUMMER to startOfSummer,
        Season.AUTUMN to startOfAutumn,
        Season.WINTER to startOfWinter,
    )
}

private fun getCurrentSeason(
    dayOfYear: Int,
    seasonStart: Map<Season, Instant>,
): Season =
    when {
        dayOfYear < seasonStart.getValue(Season.SPRING).toUtcDayOfYear() -> Season.WINTER
        dayOfYear < seasonStart.getValue(Season.SUMMER).toUtcDayOfYear() -> Season.SPRING
        dayOfYear < seasonStart.getValue(Season.AUTUMN).toUtcDayOfYear() -> Season.SUMMER
        dayOfYear < seasonStart.getValue(Season.WINTER).toUtcDayOfYear() -> Season.AUTUMN
        else -> Season.WINTER
    }

private fun getDayOfSeason(
    currentSeason: Season,
    dayOfYear: Int,
    seasonStarts: Map<Season, Instant>,
    prevWinterStart: Instant,
): Int {
    val startOfSpring = seasonStarts.getValue(Season.SPRING)
    val startOfSummer = seasonStarts.getValue(Season.SUMMER)
    val startOfAutumn = seasonStarts.getValue(Season.AUTUMN)
    val startOfWinter = seasonStarts.getValue(Season.WINTER)

    val winterDuration = prevWinterStart.daysUntil(startOfSpring, TimeZone.UTC)

    return when (currentSeason) {
        Season.SPRING -> dayOfYear - startOfSpring.toUtcDayOfYear() + 1
        Season.SUMMER -> dayOfYear - startOfSummer.toUtcDayOfYear() + 1
        Season.AUTUMN -> dayOfYear - startOfAutumn.toUtcDayOfYear() + 1
        else ->
            if (dayOfYear >= startOfWinter.toUtcDayOfYear()) {
                dayOfYear - startOfWinter.toUtcDayOfYear() + 1
            } else {
                winterDuration - (startOfSpring.toUtcDayOfYear() - dayOfYear) + 1
            }
    }
}

private fun Instant.toUtcDayOfYear() = this.toLocalDateTime(TimeZone.UTC).dayOfYear
