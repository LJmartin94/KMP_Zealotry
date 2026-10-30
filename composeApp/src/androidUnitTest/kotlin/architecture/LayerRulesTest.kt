package architecture

import kotlin.test.Test
import kotlin.test.assertTrue

class LayerRulesTest {
    @Test
    fun `no dp or sp literals outside the style package`() {
        val styleFiles = ProductionScope.files.filter { it.packageName().startsWith(STYLE_PACKAGE) }
        assertTrue(styleFiles.isNotEmpty(), "Rule is vacuous: no files found in $STYLE_PACKAGE")

        val violations =
            (ProductionScope.files - styleFiles.toSet()).flatMap { file ->
                file.text.lines().mapIndexedNotNull { index, line ->
                    val code = line.trim()
                    val isComment = listOf("//", "*", "/*").any { code.startsWith(it) }
                    if (!isComment && DIMENSION_LITERAL.containsMatchIn(code)) {
                        "${file.displayPath()}:${index + 1}: $code"
                    } else {
                        null
                    }
                }
            }

        assertNoViolations("Dimension literals (.dp/.sp) belong in $STYLE_PACKAGE", violations)
    }

    @Test
    fun `no composable functions in the data layer`() {
        val dataFiles = ProductionScope.files.filter { it.packageName().startsWith(DATA_PACKAGE) }
        assertTrue(dataFiles.isNotEmpty(), "Rule is vacuous: no files found in $DATA_PACKAGE")

        val violations =
            ProductionScope.scope
                .functions(includeNested = true)
                .filter { it.containingFile.packageName().startsWith(DATA_PACKAGE) }
                .filter { it.hasAnnotationWithName("Composable") }
                .map { "${it.containingFile.displayPath()}: ${it.name}" }

        assertNoViolations("@Composable is not allowed in the data layer", violations)
    }

    @Test
    fun `each screen file obtains at most one view model`() {
        val screenFiles = ProductionScope.files.filter { it.name.endsWith("Screen") }
        assertTrue(screenFiles.isNotEmpty(), "Rule is vacuous: no *Screen files found")

        val violations =
            screenFiles.mapNotNull { file ->
                val count = codeLines(file.text).count { GET_VIEW_MODEL_CALL.containsMatchIn(it) }
                if (count > 1) "${file.displayPath()}: $count getViewModel calls" else null
            }

        assertNoViolations("A view obtains exactly one ViewModel", violations)
    }

    @Test
    fun `only screen files call getViewModel`() {
        val presentationFiles =
            ProductionScope.files.filter { it.packageName().startsWith(PRESENTATION_PACKAGE) }
        val nonScreenFiles = presentationFiles.filterNot { it.name.endsWith("Screen") }
        assertTrue(nonScreenFiles.isNotEmpty(), "Rule is vacuous: no non-Screen presentation files")

        val violations =
            nonScreenFiles.flatMap { file ->
                codeLines(file.text)
                    .filter { GET_VIEW_MODEL_CALL.containsMatchIn(it) }
                    .map { "${file.displayPath()}: ${it.trim()}" }
            }

        assertNoViolations("Only a *Screen file may obtain a ViewModel", violations)
    }

    @Test
    fun `view models are only created through getViewModel and the DI module`() {
        val inspectedFiles =
            ProductionScope.files.filterNot {
                val packageName = it.packageName()
                packageName.startsWith(TOAD_PACKAGE) ||
                    packageName.startsWith(DI_PACKAGE) ||
                    it.name.endsWith("ViewModel")
            }
        assertTrue(inspectedFiles.isNotEmpty(), "Rule is vacuous: no files inspected")

        val violations =
            inspectedFiles.flatMap { file ->
                codeLines(file.text)
                    .filter { line -> VIEW_MODEL_BYPASSES.any { it.containsMatchIn(line) } }
                    .map { "${file.displayPath()}: ${it.trim()}" }
            }

        assertNoViolations(
            "ViewModels must come from getViewModel, not be built by hand",
            violations,
        )
    }

    private fun codeLines(text: String): List<String> =
        text.lines().filterNot { line ->
            listOf("//", "*", "/*").any { line.trim().startsWith(it) }
        }

    private companion object {
        const val STYLE_PACKAGE = "presentation.style"
        const val DATA_PACKAGE = "data"
        const val PRESENTATION_PACKAGE = "presentation"
        const val TOAD_PACKAGE = "toad"
        const val DI_PACKAGE = "di"
        val DIMENSION_LITERAL = Regex("""\b\d[\d_]*(\.\d+)?f?\.(dp|sp)\b""")

        // Matches both getViewModel<T>() and the inferred form `val vm: T = getViewModel()`.
        val GET_VIEW_MODEL_CALL = Regex("""\bgetViewModel\s*[<(]""")

        // A constructor call such as ExampleViewModel(...), or androidx's viewModel { ... }.
        val VIEW_MODEL_BYPASSES =
            listOf(
                Regex("""\b[A-Z]\w*ViewModel\s*\("""),
                Regex("""(?<![\w.])viewModel\s*[{(]"""),
            )
    }
}
