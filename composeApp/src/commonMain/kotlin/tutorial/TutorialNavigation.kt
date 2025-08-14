package tutorial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import presentation.screens.tutorial.home.HomeScreen
import presentation.screens.tutorial.task.TaskScreen

// https://developer.android.com/guide/navigation/design#compose-arguments
// https://stackoverflow.com/questions/78858250/type-safe-navigation-custom-list-navtype

@Composable
fun TutorialNavigation(navController: NavHostController = rememberNavController()) {
    val navigateTo = remember {
        {destination: NavDestination -> navController.navigate(route = destination)} //TODO: Basic temp solution
    }

    NavHost(navController = navController, startDestination = NavDestination.Home) {
        //Destination loaded without payload
        composable<NavDestination.Home> {
            HomeScreen(
                    onNavigateTo = {route -> navigateTo(route)}
            )
        }

        //Destination loaded with payload
        composable<NavDestination.Task> {backstackEntry ->
            val task: NavDestination.Task = backstackEntry.toRoute()
            TaskScreen(
                destinationContent = task,
                onBack = {navController.popBackStack()},
            )
        }
    }
}

// Screens
@Serializable
sealed class NavDestination {
    //Without payload
    @Serializable
    object Home: NavDestination()

    //With payload
    @Serializable
    data class Task(val dbLoadObject: /*ObjectId?*/ String?): NavDestination()
}