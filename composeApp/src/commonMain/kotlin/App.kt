
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.style.DarkThemeCompositionLocal
import presentation.style.MainTheme
import presentation.style.dimIfDarkMode
import presentation.style.isDark
import navigation.tutorial.TutorialNavigation

@Composable
@Preview
fun App() {
    CompositionLocalProvider(DarkThemeCompositionLocal provides isDark()) {
        MainTheme {
            Surface(modifier = Modifier.fillMaxSize().dimIfDarkMode()) {
                //navigation.Navigation() //Non-voyager nav from navigation.Navigation.kt
                TutorialNavigation() //Temp navigation function to transition from voyager library to androidx.navigation
            }
        }
    }
}
