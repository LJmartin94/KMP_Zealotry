package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.declaration.KoFileDeclaration

// Konsist is JVM-only, so these tests run as Android unit tests but must still analyse the
// multiplatform sources. Rules go through this scope so a mis-scoped rule fails loudly instead of
// passing vacuously on zero files.
object ProductionScope {
    private const val COMMON_MAIN = "composeApp/src/commonMain/"

    val scope: KoScope by lazy { Konsist.scopeFromDirectory(COMMON_MAIN) }

    val files: List<KoFileDeclaration> by lazy {
        scope.files.also {
            check(it.isNotEmpty()) { "Konsist found no files under $COMMON_MAIN" }
        }
    }
}
