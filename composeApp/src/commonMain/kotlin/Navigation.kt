
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import presentation.screens.dayPartMenu.DayPartMenuScreen
import presentation.screens.dayPartMenu.DayPartMenuViewModel
import presentation.screens.mainMenu.MainMenuScreen
import presentation.screens.mainMenu.MainMenuViewModel

@Composable
fun NavigationNew() {
    val navController = rememberNavController()
}

@Composable
fun Navigation() {
    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = "/mainMenu",
    ) {
        scene(route = "/mainMenu", navTransition = NavTransition()) {
            val mainVM = koinViewModel(MainMenuViewModel::class)
            MainMenuScreen(mainVM) { navigator.navigate("/dayPartMenu/$it") }
        }

        scene(route = "/dayPartMenu/{part}", navTransition = NavTransition()) {
            val vm = koinViewModel(DayPartMenuViewModel::class)
            DayPartMenuScreen(vm)
        }
    }
}
