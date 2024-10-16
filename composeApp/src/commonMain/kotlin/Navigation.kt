
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import data.calendar.CalendarRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import presentation.screens.dayPartMenu.DayPartMenuScreen
import presentation.screens.dayPartMenu.DayPartMenuViewModel
import presentation.screens.mainMenu.MainMenuScreen
import presentation.screens.mainMenu.MainMenuViewModel
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.day
import zealotry.composeapp.generated.resources.empty
import zealotry.composeapp.generated.resources.evening
import zealotry.composeapp.generated.resources.morning

// Screens
@OptIn(ExperimentalResourceApi::class)
enum class Screen(val title: StringResource) {
    MainMenu(title = Res.string.empty),
    Morning(title = Res.string.morning),
    Day(title = Res.string.day),
    Evening(title = Res.string.evening),
}

@Composable
fun InitNavStack() {
    val navController: NavHostController = rememberNavController()

    // ViewModels
    val mainVM = viewModel { MainMenuViewModel(CalendarRepository()) }
    val dayPartVM = viewModel { DayPartMenuViewModel() }

    NavHost(navController, startDestination = Screen.MainMenu.name) {
        composable(route = Screen.MainMenu.name) {
            MainMenuScreen(viewModel = mainVM) { navController.navigate(it) }
        }
        composable(route = Screen.Morning.name) { DayPartMenuScreen(dayPartVM) }
        composable(route = Screen.Day.name) { DayPartMenuScreen(dayPartVM) }
        composable(route = Screen.Evening.name) { DayPartMenuScreen(dayPartVM) }
    }
}
