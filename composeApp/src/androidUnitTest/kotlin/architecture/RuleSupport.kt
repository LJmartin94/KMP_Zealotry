package architecture

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import kotlin.test.assertTrue

internal fun KoFileDeclaration.packageName(): String = packagee?.name.orEmpty()

internal fun KoFileDeclaration.displayPath(): String = path.substringAfter("composeApp/")

// Fails with one line per violation, so a red run shows everything to fix rather than the first hit.
internal fun assertNoViolations(
    rule: String,
    violations: List<String>,
) {
    val message = "$rule — ${violations.size} violation(s):\n" + violations.joinToString("\n")
    assertTrue(violations.isEmpty(), message)
}
