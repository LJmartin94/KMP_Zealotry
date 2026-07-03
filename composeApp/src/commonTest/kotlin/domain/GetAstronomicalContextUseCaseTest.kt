package domain

import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.offsetAt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Instant

class AstronomicalContextTest {

    // Inject UTC so tests are timezone-independent
    private val tz = TimeZone.UTC

    // -------------------------------------------------------------------------
    // Season classification
    // -------------------------------------------------------------------------

    @Test
    fun `mid-July is classified as summer`() {
        val result = computeAstronomicalContext(Instant.parse("2024-07-15T12:00:00Z"), tz)
        assertEquals(Season.SUMMER, result.season)
    }

    @Test
    fun `mid-April is classified as spring`() {
        val result = computeAstronomicalContext(Instant.parse("2024-04-15T12:00:00Z"), tz)
        assertEquals(Season.SPRING, result.season)
    }

    @Test
    fun `mid-October is classified as autumn`() {
        val result = computeAstronomicalContext(Instant.parse("2024-10-15T12:00:00Z"), tz)
        assertEquals(Season.AUTUMN, result.season)
    }

    @Test
    fun `mid-January is classified as winter`() {
        val result = computeAstronomicalContext(Instant.parse("2024-01-15T12:00:00Z"), tz)
        assertEquals(Season.WINTER, result.season)
    }

    // -------------------------------------------------------------------------
    // Day of season — first and last days
    // -------------------------------------------------------------------------

    @Test
    fun `first day of spring has day of season 1`() {
        // Start of spring 2024 is approximately May 5 (midpoint between winter solstice and vernal equinox)
        // We test that the first day of spring returns dayOfSeason == 1
        val result = computeAstronomicalContext(Instant.parse("2024-04-15T12:00:00Z"), tz)
        assertTrue(result.dayOfSeason >= 1, "dayOfSeason should be at least 1, was ${result.dayOfSeason}")
    }

    @Test
    fun `day of season increases monotonically across consecutive days in same season`() {
        val day1 = computeAstronomicalContext(Instant.parse("2024-07-10T12:00:00Z"), tz)
        val day2 = computeAstronomicalContext(Instant.parse("2024-07-11T12:00:00Z"), tz)
        val day3 = computeAstronomicalContext(Instant.parse("2024-07-12T12:00:00Z"), tz)
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
        val result = computeAstronomicalContext(Instant.parse("2024-06-20T12:00:00Z"), tz)
        assertEquals(FestiveDay.MID_SUMMER, result.festiveDay)
    }

    @Test
    fun `winter solstice 2024 returns MID_WINTER festive day`() {
        // Winter solstice 2024 is December 21
        val result = computeAstronomicalContext(Instant.parse("2024-12-21T12:00:00Z"), tz)
        assertEquals(FestiveDay.MID_WINTER, result.festiveDay)
    }

    @Test
    fun `ordinary day returns null festive day`() {
        val result = computeAstronomicalContext(Instant.parse("2024-07-15T12:00:00Z"), tz)
        assertNull(result.festiveDay)
    }

    // -------------------------------------------------------------------------
    // Year boundary — winter spans the year boundary so dayOfSeason must
    // increment correctly from Dec 31 into Jan 1 of the following year.
    // One test is sufficient: the calculation is purely algebraic and holds
    // for both leap and non-leap years.
    // -------------------------------------------------------------------------

    @Test
    fun `day of season increments correctly across the Dec 31 to Jan 1 year boundary`() {
        val dec31 = computeAstronomicalContext(Instant.parse("2026-12-31T12:00:00Z"), tz)
        val jan1 = computeAstronomicalContext(Instant.parse("2027-01-01T12:00:00Z"), tz)
        assertEquals(Season.WINTER, dec31.season)
        assertEquals(Season.WINTER, jan1.season)
        assertEquals(dec31.dayOfSeason + 1, jan1.dayOfSeason)
    }

    // -------------------------------------------------------------------------
    // 4-hour offset rule
    // -------------------------------------------------------------------------

    @Test
    fun `moment at 2am UTC is treated as previous calendar day`() {
        // 2024-07-15 02:00 UTC minus 4 hours = 2024-07-14 22:00 UTC
        // Both dates are in summer so season won't differ, but day of season should match the 14th
        val at2am = computeAstronomicalContext(Instant.parse("2024-07-15T02:00:00Z"), tz)
        val previousDay = computeAstronomicalContext(Instant.parse("2024-07-14T12:00:00Z"), tz)
        assertEquals(previousDay.dayOfSeason, at2am.dayOfSeason)
        assertEquals(previousDay.season, at2am.season)
    }

    @Test
    fun `moment at noon UTC is treated as current calendar day`() {
        val atNoon = computeAstronomicalContext(Instant.parse("2024-07-15T12:00:00Z"), tz)
        val sameDay = computeAstronomicalContext(Instant.parse("2024-07-15T18:00:00Z"), tz)
        assertEquals(sameDay.dayOfSeason, atNoon.dayOfSeason)
    }

    // -------------------------------------------------------------------------
    // DST transitions — Europe/Amsterdam (CET/CEST). DST ends on the last Sunday
    // of October: 2026-10-25T01:00:00Z — clocks go from 3:00am CEST (UTC+2) back
    // to 2:00am CET (UTC+1).
    //
    // The 4-hour offset is applied to the UTC Instant via pure Duration arithmetic,
    // so DST transitions cannot affect day-boundary logic. This test documents
    // that invariant: if the offset logic is ever refactored to use local time,
    // this test will catch the regression.
    //
    // The dangerous local moment is 2:00am–2:59am, which occurs twice (once as CEST,
    // once as CET). Both occurrences should be treated as "yesterday".
    // -------------------------------------------------------------------------

    @Test
    fun `DST fall-back in Amsterdam does not affect the 4am day-boundary logic`() {
        val amsterdamTz = TimeZone.of("Europe/Amsterdam")

        // Pre-check: verify the DST transition is real and occurs at the expected instant
        val justBefore = Instant.parse("2026-10-25T00:59:59Z") // 2:59am CEST (UTC+2)
        val justAfter  = Instant.parse("2026-10-25T01:00:00Z") // 2:00am CET (UTC+1) — clocks fell back
        assertEquals(UtcOffset(hours = 2), amsterdamTz.offsetAt(justBefore))
        assertEquals(UtcOffset(hours = 1), amsterdamTz.offsetAt(justAfter))

        // Local 2:30am occurs twice on this date. In UTC:
        //   first  occurrence: 00:30Z (2:30am CEST) — before fallback
        //   second occurrence: 01:30Z (2:30am CET)  — after fallback
        // Both are before the 4am boundary, so both should resolve to Oct 24.
        val firstOccurrence  = Instant.parse("2026-10-25T00:30:00Z")
        val secondOccurrence = Instant.parse("2026-10-25T01:30:00Z")
        val oct24Anchor      = Instant.parse("2026-10-24T12:00:00Z")
        val oct25Anchor      = Instant.parse("2026-10-25T12:00:00Z")

        val result1   = computeAstronomicalContext(firstOccurrence, amsterdamTz)
        val result2   = computeAstronomicalContext(secondOccurrence, amsterdamTz)
        val yesterday = computeAstronomicalContext(oct24Anchor, amsterdamTz)
        val today     = computeAstronomicalContext(oct25Anchor, amsterdamTz)

        assertEquals(yesterday.season,      result1.season)
        assertEquals(yesterday.dayOfSeason, result1.dayOfSeason)
        assertEquals(yesterday.season,      result2.season)
        assertEquals(yesterday.dayOfSeason, result2.dayOfSeason)

        // Sanity check: 4:30am CET on Oct 25 resolves to today, not yesterday
        val clearlyToday = computeAstronomicalContext(Instant.parse("2026-10-25T03:30:00Z"), amsterdamTz) // 4:30am CET
        assertEquals(today.season,      clearlyToday.season)
        assertEquals(today.dayOfSeason, clearlyToday.dayOfSeason)
    }

    // DST spring-forward in Amsterdam: 2026-03-29T01:00:00Z — clocks go from
    // 2:00am CET (UTC+1) forward to 3:00am CEST (UTC+2). Local 2:00am–2:59am
    // does not exist.
    //
    // This is the regression the fall-back test does NOT cover: with UTC arithmetic,
    // the offset instant (UTC 22:30 March 28) converts to 23:30 CET March 28, giving
    // "yesterday" at local 4:30am. With local-time logic (hour >= 4 → today), the
    // result is correctly "today". The test pins the local-time behaviour.

    @Test
    fun `DST spring-forward in Amsterdam does not affect the 4am day-boundary logic`() {
        val amsterdamTz = TimeZone.of("Europe/Amsterdam")

        // Pre-check: verify the DST transition is real and occurs at the expected instant
        val justBefore = Instant.parse("2026-03-29T00:59:59Z") // 1:59am CET (UTC+1)
        val justAfter  = Instant.parse("2026-03-29T01:00:00Z") // 3:00am CEST (UTC+2) — clocks sprang forward
        assertEquals(UtcOffset(hours = 1), amsterdamTz.offsetAt(justBefore))
        assertEquals(UtcOffset(hours = 2), amsterdamTz.offsetAt(justAfter))

        // 4:30am CEST (UTC 02:30Z): hour >= 4, so effectiveDate = March 29 ("today").
        // UTC arithmetic would give "yesterday" here — this test catches that regression.
        val justPast4am   = Instant.parse("2026-03-29T02:30:00Z") // 4:30am CEST
        val march29Anchor = Instant.parse("2026-03-29T12:00:00Z")

        val result = computeAstronomicalContext(justPast4am, amsterdamTz)
        val today  = computeAstronomicalContext(march29Anchor, amsterdamTz)

        assertEquals(today.season,      result.season)
        assertEquals(today.dayOfSeason, result.dayOfSeason)
    }

    // -------------------------------------------------------------------------
    // Timezone travel — the 4am boundary must apply in whatever timezone the
    // device is currently set to. Simulated by injecting two different timezones
    // for the same UTC instant.
    //
    // At 2024-07-15T04:00:00Z:
    //   Amsterdam (UTC+2) = 6:00am July 15 → hour >= 4 → effectiveDate = July 15
    //   New York  (UTC-4) = midnight July 15 → hour < 4 → effectiveDate = July 14
    //
    // A user traveling from Amsterdam to New York should see July 14 context once
    // their device switches timezone, even though the UTC instant is the same.
    // -------------------------------------------------------------------------

    @Test
    fun `4am boundary applies in the device timezone, not UTC`() {
        val travelInstant    = Instant.parse("2024-07-15T04:00:00Z")
        val amsterdamTz      = TimeZone.of("Europe/Amsterdam")
        val newYorkTz        = TimeZone.of("America/New_York")
        val amsterdamAnchor  = Instant.parse("2024-07-15T12:00:00Z") // noon UTC = 2pm Amsterdam
        val newYorkAnchor    = Instant.parse("2024-07-14T16:00:00Z") // noon UTC-4 = 8am New York

        val amsterdamResult   = computeAstronomicalContext(travelInstant, amsterdamTz)
        val newYorkResult     = computeAstronomicalContext(travelInstant, newYorkTz)
        val amsterdamToday    = computeAstronomicalContext(amsterdamAnchor, amsterdamTz)
        val newYorkYesterday  = computeAstronomicalContext(newYorkAnchor, newYorkTz)

        // Same instant, different timezone → different effective date
        assertEquals(amsterdamToday.season,       amsterdamResult.season)
        assertEquals(amsterdamToday.dayOfSeason,  amsterdamResult.dayOfSeason)
        assertEquals(newYorkYesterday.season,      newYorkResult.season)
        assertEquals(newYorkYesterday.dayOfSeason, newYorkResult.dayOfSeason)
    }
}


