package architecture

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import kotlin.test.Test
import kotlin.test.assertTrue

// User-facing text must come from string resources, so every string literal in production code is
// a violation unless it matches a named entry in ALLOWED. The default is "flag it": add a new
// entry (with a reason) only for strings that are demonstrably developer-facing.
//
// Matching works on the call that encloses each literal, because Konsist exposes no
// expression-level API. The file is scanned character by character (skipping comments and string
// contents) keeping a stack of the calls still open. A literal is allowed if an entry matches the
// line text before one of the calls enclosing it, or its own line (plus the previous line when
// that ends in `=`, for `const val X =\n "value"`). So a wrapped `error(\n "a" +\n "b",\n)` is
// allowed however long it is, while `Text("Hello")` after a closed `Logger.d("x")` is not.
class HardcodedStringsRuleTest {
    @Test
    fun `no hardcoded strings outside the whitelist`() {
        val violations =
            literals()
                .filter { it.allowedBy == null }
                .map { "${it.file.displayPath()}:${it.lineNumber}: ${it.code}" }
                .distinct()

        assertNoViolations(
            "User-facing text belongs in string resources; whitelist developer-facing strings",
            violations,
        )
    }

    private class Literal(
        val file: KoFileDeclaration,
        val lineNumber: Int,
        val code: String,
        val allowedBy: AllowedStrings?,
    )

    private class AllowedStrings(
        val reason: String,
        val matches: (file: KoFileDeclaration, context: String) -> Boolean,
    )

    // lineIndex is zero based; enclosingCalls holds the line text up to each still-open "(".
    private class LiteralSite(
        val lineIndex: Int,
        val enclosingCalls: List<String>,
    )

    private fun literals(): List<Literal> {
        assertTrue(ProductionScope.files.isNotEmpty(), "Rule is vacuous: no production files")

        return ProductionScope.files.flatMap { file ->
            val lines = file.text.lines()
            findLiteralSites(file.text).map { site ->
                val context =
                    (site.enclosingCalls + statementAt(lines, site.lineIndex)).joinToString("\n")
                Literal(
                    file = file,
                    lineNumber = site.lineIndex + 1,
                    code = lines[site.lineIndex].trim(),
                    allowedBy = ALLOWED.firstOrNull { it.matches(file, context) },
                )
            }
        }
    }

    private fun statementAt(
        lines: List<String>,
        lineIndex: Int,
    ): String {
        val previous = lines.getOrNull(lineIndex - 1)?.trim().orEmpty()
        return if (previous.endsWith("=")) previous + "\n" + lines[lineIndex] else lines[lineIndex]
    }

    private fun findLiteralSites(text: String): List<LiteralSite> {
        val lineStarts = listOf(0) + text.indices.filter { text[it] == '\n' }.map { it + 1 }

        fun lineIndexOf(offset: Int) =
            lineStarts.binarySearch(offset).let { if (it >= 0) it else -it - 2 }

        val sites = mutableListOf<LiteralSite>()
        val openCalls = ArrayDeque<String>()
        var i = 0
        while (i < text.length) {
            val start = i
            when {
                text.startsWith("//", i) -> i = endOfLine(text, i)
                text.startsWith("/*", i) -> i = skipPast(text, "*/", i + 2)
                text[i] == '\'' -> i = skipCharLiteral(text, i)
                text.startsWith("\"\"\"", i) -> i = skipPast(text, "\"\"\"", i + 3)
                text[i] == '"' -> i = endOfString(text, i)
                text[i] == '(' -> {
                    openCalls.addLast(text.substring(lineStarts[lineIndexOf(i)], i + 1).trim())
                    i++
                }
                text[i] == '{' -> {
                    openCalls.addLast(trailingLambdaOwner(text, lineStarts[lineIndexOf(i)], i))
                    i++
                }
                text[i] == ')' || text[i] == '}' -> {
                    openCalls.removeLastOrNull()
                    i++
                }
                else -> i++
            }
            val isNonEmptyString = text[start] == '"' && i - start > 2
            if (isNonEmptyString) sites += LiteralSite(lineIndexOf(start), openCalls.toList())
        }
        return sites
    }

    // A trailing lambda belongs to its call: `check(x) {\n "message"\n}`. Declarations and control
    // flow also end in `) {`, but their bodies are not arguments of a call.
    private fun trailingLambdaOwner(
        text: String,
        lineStart: Int,
        braceOffset: Int,
    ): String {
        val beforeBrace = text.substring(lineStart, braceOffset).trim()
        val isTrailingLambda =
            beforeBrace.endsWith(")") && !DECLARATION_OR_CONTROL.containsMatchIn(beforeBrace)
        return if (isTrailingLambda) beforeBrace else ""
    }

    private fun endOfLine(
        text: String,
        from: Int,
    ): Int = text.indexOf('\n', from).let { if (it < 0) text.length else it }

    private fun skipPast(
        text: String,
        token: String,
        from: Int,
    ): Int = text.indexOf(token, from).let { if (it < 0) text.length else it + token.length }

    private fun skipCharLiteral(
        text: String,
        start: Int,
    ): Int {
        val isEscaped = text.getOrNull(start + 1) == '\\'
        val closing = if (isEscaped) text.indexOf('\'', start + 3) else start + 2
        return if (closing < 0) text.length else closing + 1
    }

    // Returns the offset just past the closing quote, skipping escapes and ${...} templates.
    private fun endOfString(
        text: String,
        start: Int,
    ): Int {
        var i = start + 1
        while (i < text.length) {
            when {
                text[i] == '\\' -> i += 2
                text[i] == '"' -> return i + 1
                text.startsWith("\${", i) -> i = endOfTemplate(text, i + 2)
                else -> i++
            }
        }
        return text.length
    }

    private fun endOfTemplate(
        text: String,
        from: Int,
    ): Int {
        var depth = 1
        var i = from
        while (i < text.length && depth > 0) {
            if (text[i] == '{') depth++
            if (text[i] == '}') depth--
            i++
        }
        return i
    }

    private companion object {
        val DECLARATION_OR_CONTROL = Regex("""^(.*\s)?(fun|if|for|while|when)\b""")

        val ALLOWED =
            listOf(
                contextEntry(
                    reason = "Annotation arguments (@Suppress, @OptIn, ...)",
                    pattern = Regex("""@[\w:]+\("""),
                ),
                contextEntry(
                    reason = "Constants (const val, e.g. log tags)",
                    pattern = Regex("""\bconst\s+val\b"""),
                ),
                contextEntry(
                    reason = "Log messages (Logger.x)",
                    pattern = Regex("""\bLogger\.\w+\("""),
                ),
                contextEntry(
                    reason = "Developer-facing failures (error/check/require/throw)",
                    pattern =
                        Regex(
                            """\b(error|check|require|checkNotNull|requireNotNull)\s*\(|\bthrow\b""",
                        ),
                ),
                contextEntry(
                    reason = "Machine-readable date/time constants (parse, parseIsoString)",
                    pattern = Regex("""\b(parse|parseIsoString)\("""),
                ),
                AllowedStrings("Locale language codes in util.localisation.Locale") { file, _ ->
                    file.name == "Locale" && file.packageName() == "util.localisation"
                },
                AllowedStrings("Data layer: SQL, table names, storage keys") { file, _ ->
                    file.packageName().let { it == "data" || it.startsWith("data.") }
                },
            )

        fun contextEntry(
            reason: String,
            pattern: Regex,
        ) = AllowedStrings(reason) { _, context -> pattern.containsMatchIn(context) }
    }
}
