package architecture

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import kotlin.test.Test
import kotlin.test.assertTrue

// Presentation talks to data only through injected dependencies (see ARCHITECTURE_NOTES, "TOAD
// boundary rules"). The roles below are identified by the file-name convention used by every feature.
class BoundaryRulesTest {
    @Test
    fun `actions never import the data layer`() {
        val actionFiles = presentationFiles.filter { it.isAction() }
        assertTrue(actionFiles.isNotEmpty(), "Rule is vacuous: no *Action files found")

        assertNoViolations(
            "Actions reach data only through injected ActionDependencies",
            actionFiles.flatMap { it.importsFrom(DATA_PACKAGE) },
        )
    }

    @Test
    fun `view side files never import the data layer`() {
        val viewSideFiles = presentationFiles.filterNot { it.isAction() || it.isWiring() }
        assertTrue(viewSideFiles.isNotEmpty(), "Rule is vacuous: no view-side files found")

        assertNoViolations(
            "Screens, UI state and UI components must not depend on data types",
            viewSideFiles.flatMap { it.importsFrom(DATA_PACKAGE) },
        )
    }

    @Test
    fun `wiring files import only repositories from the data layer`() {
        val wiringFiles = presentationFiles.filter { it.isWiring() }
        assertTrue(
            wiringFiles.isNotEmpty(),
            "Rule is vacuous: no ViewModel/ActionDependencies found",
        )

        assertNoViolations(
            "ViewModels and ActionDependencies may name repositories, never entities",
            wiringFiles.flatMap { file ->
                file.importsFrom(DATA_PACKAGE).filterNot { it.endsWith("Repository") }
            },
        )
    }

    // The package is deliberately allowed to be empty until DayPart moves into it (phase 2), so this
    // rule has no vacuity guard. It was proven with a temporary violation instead.
    @Test
    fun `model package depends on no other layer`() {
        val modelFiles = ProductionScope.files.filter { it.packageName().startsWith(MODEL_PACKAGE) }

        assertNoViolations(
            "The shared model package must not import any other layer",
            modelFiles.flatMap { file ->
                LAYER_PACKAGES.flatMap { layer -> file.importsFrom(layer) }
            },
        )
    }

    private val presentationFiles: List<KoFileDeclaration>
        get() = ProductionScope.files.filter { it.packageName().startsWith(PRESENTATION_PACKAGE) }

    private fun KoFileDeclaration.isAction() = name.endsWith("Action") || name.endsWith("Actions")

    private fun KoFileDeclaration.isWiring() =
        name.endsWith("ViewModel") ||
            name.endsWith("ActionDependencies")

    private fun KoFileDeclaration.importsFrom(packagePrefix: String): List<String> =
        imports
            .map { it.name }
            .filter { it == packagePrefix || it.startsWith("$packagePrefix.") }
            .map { "${displayPath()}: import $it" }

    private companion object {
        const val PRESENTATION_PACKAGE = "presentation"
        const val DATA_PACKAGE = "data"
        const val MODEL_PACKAGE = "model"
        val LAYER_PACKAGES = listOf("data", "domain", "presentation", "toad", "navigation", "di")
    }
}
