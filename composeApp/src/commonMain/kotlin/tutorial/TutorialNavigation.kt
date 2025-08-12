package tutorial


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import presentation.screens.tutorial.home.HomeScreen
import presentation.screens.tutorial.task.TaskScreen

@Composable
fun TutorialNavigation(navController: NavHostController = rememberNavController()) {
    val navigateTo = remember {
        {destination: NavDestination -> navController.navigate(route = destination)} //TODO: Basic temp solution
    }

    NavHost(navController = navController, startDestination = NavDestination.Home) {
        composable<NavDestination.Home> {
            HomeScreen(
                    onNavigateTo = {navigateTo(it)}
//                    navigateToTask = { task -> navController.navigate(NavDestination.Task.name) }
            )
        }

        composable<NavDestination.Task> {
            TaskScreen(
                onNavigateTo = {navigateTo(it)},
                onBack = {navController.popBackStack()}
            )
        }
    }
}

// Screens
@Serializable
sealed interface NavDestination {
    @Serializable
    object Home: NavDestination
    @Serializable
    object Task: NavDestination
}
