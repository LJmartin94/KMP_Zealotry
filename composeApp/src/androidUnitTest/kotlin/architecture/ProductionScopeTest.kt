package architecture

import java.io.File
import kotlin.test.Test

// The rules scan commonMain only (see ProductionScope). This stops code added to any other source
// set from silently escaping them: a new source set must be scanned or excluded here, with a reason.
class ProductionScopeTest {
    @Test
    fun `every production source set is scanned or deliberately excluded`() {
        val unaccounted = productionSourceSets() - SCANNED - UNSCANNED.keys

        assertNoViolations(
            "Source sets not covered by the architecture rules " +
                "(scan them in ProductionScope or exclude them in ProductionScopeTest)",
            unaccounted.sorted(),
        )
    }

    @Test
    fun `every exclusion still names an existing source set`() {
        val stale = UNSCANNED.keys - productionSourceSets()

        assertNoViolations("Exclusions naming a source set that no longer exists", stale.sorted())
    }

    private fun productionSourceSets(): Set<String> {
        val anyFilePath = ProductionScope.files.first().path
        val sourceRoot = File(anyFilePath.substringBefore("/$SCANNED/"))

        return sourceRoot
            .listFiles { file -> file.isDirectory }
            .orEmpty()
            .map { it.name }
            .filterNot { it.endsWith("Test") }
            .toSet()
    }

    private companion object {
        const val SCANNED = "commonMain"

        val UNSCANNED =
            mapOf(
                "androidMain" to "Platform glue: MainActivity, Room's Android database builder",
                "iosMain" to "Platform glue: MainViewController, Room's iOS database builder",
            )
    }
}
