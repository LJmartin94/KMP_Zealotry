package tutorial


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import presentation.screens.tutorial.home.HomeScreen
import presentation.screens.tutorial.task.TaskScreen

@Composable
fun TutorialNavigation(navController: NavHostController = rememberNavController()) {
//    val navigateTo = remember {destination: NavDestination ->
//        {navController.navigate(route = destination)}
//    }


    NavHost(navController, startDestination = NavDestination.Home.name) {
        composable(route = NavDestination.Home.name) {
            HomeScreen(
                    navigateToTask = { task -> navController.navigate(NavDestination.Task.name) }
            )
        }

        composable(route = NavDestination.Task.name) {
            TaskScreen(
                    navigateBack = {navController.popBackStack()}
            )
        }
    }
}

// Screens
enum class NavDestination {
    Home,
    Task,
}
