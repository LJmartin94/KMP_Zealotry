
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.reusableUi.LocalSnackbarHostState
import presentation.style.DarkThemeCompositionLocal
import presentation.style.MainTheme
import presentation.style.dimIfDarkMode
import presentation.style.isDark

@Composable
@Preview
fun App() {
    val snackbarHostState = remember { SnackbarHostState() }
    CompositionLocalProvider(
        DarkThemeCompositionLocal provides isDark(),
        LocalSnackbarHostState provides snackbarHostState,
    ) {
        MainTheme {
            Surface(modifier = Modifier.fillMaxSize().dimIfDarkMode()) {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) {
                    Navigation()
                }
            }
        }
    }
}
