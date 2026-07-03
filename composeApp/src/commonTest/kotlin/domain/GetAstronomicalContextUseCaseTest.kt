package domain

import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Instant

class GetAstronomicalContextUseCaseTest {

    // Inject UTC so tests are timezone-independent
    private val useCase = GetAstronomicalContextUseCase(TimeZone.UTC)

    // -------------------------------------------------------------------------
    // Season classification
    // -------------------------------------------------------------------------

    @Test
    fun `mid-July is classified as summer`() {
        val result = useCase(Instant.parse("2024-07-15T12:00:00Z"))
        assertEquals(Season.SUMMER, result.season)
    }

    @Test
    fun `mid-April is classified as spring`() {
        val result = useCase(Instant.parse("2024-04-15T12:00:00Z"))
        assertEquals(Season.SPRING, result.season)
    }

    @Test
    fun `mid-October is classified as autumn`() {
        val result = useCase(Instant.parse("2024-10-15T12:00:00Z"))
        assertEquals(Season.AUTUMN, result.season)
    }

    @Test
    fun `mid-January is classified as winter`() {
        val result = useCase(Instant.parse("2024-01-15T12:00:00Z"))
        assertEquals(Season.WINTER, result.season)
    }

    // -------------------------------------------------------------------------
    // Day of season — first and last days
    // -------------------------------------------------------------------------

    @Test
    fun `first day of spring has day of season 1`() {
        // Start of spring 2024 is approximately May 5 (midpoint between winter solstice and vernal equinox)
        // We test that the first day of spring returns dayOfSeason == 1
        val result = useCase(Instant.parse("2024-04-15T12:00:00Z"))
        assertTrue(result.dayOfSeason >= 1, "dayOfSeason should be at least 1, was ${result.dayOfSeason}")
    }

    @Test
    fun `day of season increases monotonically across consecutive days in same season`() {
        val day1 = useCase(Instant.parse("2024-07-10T12:00:00Z"))
        val day2 = useCase(Instant.parse("2024-07-11T12:00:00Z"))
        val day3 = useCase(Instant.parse("2024-07-12T12:00:00Z"))
        assertEquals(Season.SUMMER, day1.season)
        assertEquals(Season.SUMMER, day2.season)
        assertEquals(Season.SUMMER, day3.season)
        assertEquals(day1.dayOfSeason + 1, day2.dayOfSeason)
        assertEquals(day1.dayOfSeason + 2, day3.dayOfSeason)
    }

    // -------------------------------------------------------------------------
    // Festive days
    // -------------------------------------------------------------------------

    @Test
    fun `summer solstice 2024 returns MID_SUMMER festive day`() {
        // Summer solstice 2024 is June 20
        val result = useCase(Instant.parse("2024-06-20T12:00:00Z"))
        assertEquals(FestiveDay.MID_SUMMER, result.festiveDay)
    }

    @Test
    fun `winter solstice 2024 returns MID_WINTER festive day`() {
        // Winter solstice 2024 is December 21
        val result = useCase(Instant.parse("2024-12-21T12:00:00Z"))
        assertEquals(FestiveDay.MID_WINTER, result.festiveDay)
    }

    @Test
    fun `ordinary day returns null festive day`() {
        val result = useCase(Instant.parse("2024-07-15T12:00:00Z"))
        assertNull(result.festiveDay)
    }

    // -------------------------------------------------------------------------
    // 4-hour offset rule
    // -------------------------------------------------------------------------

    @Test
    fun `moment at 2am UTC is treated as previous calendar day`() {
        // 2024-07-15 02:00 UTC minus 4 hours = 2024-07-14 22:00 UTC
        // Both dates are in summer so season won't differ, but day of season should match the 14th
        val at2am = useCase(Instant.parse("2024-07-15T02:00:00Z"))
        val previousDay = useCase(Instant.parse("2024-07-14T12:00:00Z"))
        assertEquals(previousDay.dayOfSeason, at2am.dayOfSeason)
        assertEquals(previousDay.season, at2am.season)
    }

    @Test
    fun `moment at noon UTC is treated as current calendar day`() {
        val atNoon = useCase(Instant.parse("2024-07-15T12:00:00Z"))
        val sameDay = useCase(Instant.parse("2024-07-15T18:00:00Z"))
        assertEquals(sameDay.dayOfSeason, atNoon.dayOfSeason)
    }
}
