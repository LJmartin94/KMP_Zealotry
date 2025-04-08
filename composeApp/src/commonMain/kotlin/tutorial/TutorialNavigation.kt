package tutorial


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import presentation.screens.tutorial.home.HomeScreen
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.empty

@Composable
fun TutorialNavigation(navController: NavHostController = rememberNavController()) {
//    // ViewModels
//    val mainVM = viewModel { MainMenuViewModel(CalendarRepository()) }
//    val dayPartVM = viewModel { DayPartMenuViewModel() }

    NavHost(navController, startDestination = Screen.Home.name) {
        composable(route = Screen.Home.name) {
            HomeScreen().Content()
        }

//        composable(route = Screen.Morning.name) {
//            dayPartVM.setDayPart(DayPart.MORNING)
//            DayPartMenuScreen(dayPartVM) { navController.navigate(Screen.Day.name) }
//        }
//        composable(route = Screen.Day.name) {
//            dayPartVM.setDayPart(DayPart.MIDDAY)
//            DayPartMenuScreen(dayPartVM) { navController.navigate(Screen.Evening.name) }
//        }
//        composable(route = Screen.Evening.name) {
//            dayPartVM.setDayPart(DayPart.EVENING)
//            DayPartMenuScreen(dayPartVM) { navController.navigate(Screen.Morning.name) }
//        }
    }
}

// Screens
@OptIn(ExperimentalResourceApi::class)
enum class Screen(val title: StringResource) {
    Home(title = Res.string.empty),
    Task(title = Res.string.empty),
}
