import androidx.compose.runtime.Composable
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import presentation.calendar.CalendarViewModel
import presentation.screens.dayPartMenu.DayPartMenuScreen
import presentation.screens.dayPartMenu.DayPartMenuViewModel
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
            val mainVM = koinViewModel(MainMenuViewModel::class)
            val calendarVM = koinViewModel(CalendarViewModel::class)
            MainMenuScreen(mainVM, calendarVM) {navigator.navigate("/dayPartMenu/$it")}
        }

        scene(route = "/dayPartMenu/{part}", navTransition = NavTransition()) {
            val vm = koinViewModel(DayPartMenuViewModel::class)
            DayPartMenuScreen(vm)
        }
    }
}