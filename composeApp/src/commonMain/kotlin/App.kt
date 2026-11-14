
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import navigation.Navigation
import presentation.reusableUi.LocalSnackbarHostState
import presentation.style.DarkThemeCompositionLocal
import presentation.style.MainTheme
import presentation.style.dimIfDarkMode
import presentation.style.isDark
import presentation.style.rememberWindowSize

@Composable
fun App() {
    val snackbarHostState = remember { SnackbarHostState() }

    val windowSize = rememberWindowSize()
    LaunchedEffect(windowSize) { //runs after successful composition and on key change
        Logger.i(tag = "Main Menu") { "WindowSize is $windowSize" }
    }

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
