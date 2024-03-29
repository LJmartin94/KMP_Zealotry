import androidx.compose.runtime.Composable
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import presentation.screens.mainMenu.MainMenuScreen
import presentation.screens.mainMenu.MainMenuViewModel

@Composable
fun Navigation() {
    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = "/mainMenu"
    ) {
        scene(route = "/mainMenu", navTransition = NavTransition()) {
            val vm = koinViewModel(MainMenuViewModel::class)
            MainMenuScreen(vm)
        }
    }
}