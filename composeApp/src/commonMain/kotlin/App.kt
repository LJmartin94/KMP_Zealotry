
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import domain.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screens.tutorial.home.HomeScreen
import presentation.style.DarkThemeCompositionLocal
import presentation.style.MainTheme
import presentation.style.dimIfDarkMode
import presentation.style.isDark

@Composable
@Preview
fun App() {
    initKoin()

    CompositionLocalProvider(DarkThemeCompositionLocal provides isDark()) {
        MainTheme {
            Surface(modifier = Modifier.fillMaxSize().dimIfDarkMode()) {
                //Navigation() //Non-voyager nav from Navigation.kt
                Navigator(HomeScreen()){
                    SlideTransition(it)
                }
            }
        }
    }
}
