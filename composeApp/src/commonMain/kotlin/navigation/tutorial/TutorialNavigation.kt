package navigation.tutorial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import domain.tutorial.ToDoTask
import kotlinx.serialization.Serializable
import navigation.navTypeOf
import org.mongodb.kbson.ObjectId
import presentation.screens.tutorial.home.HomeScreen
import presentation.screens.tutorial.task.TaskScreen
import kotlin.reflect.typeOf

// https://developer.android.com/guide/navigation/design#compose-arguments
// https://stackoverflow.com/questions/78858250/type-safe-navigation-custom-list-navtype

@Composable
fun TutorialNavigation(navController: NavHostController = rememberNavController()) {
    // Define how to navigate to a route, now we can only navigate to NavDestination type
    val navigateTo = remember { { destination: NavDestination -> navController.navigate(route = destination) } }
    // Define how to pass keys around for loading items from db: avoid passing the objects themselves
    val typeMap = mapOf(typeOf<ObjectId?>() to navTypeOf<ObjectId?>())

    NavHost(navController = navController, startDestination = NavDestination.Home) {
        // Destinations loaded without payload
        composable<NavDestination.Home> {
            HomeScreen(
                onNavigateTo = { route -> navigateTo(route) },
            )
        }

        // Destinations loaded with payload
        composable<NavDestination.Task>(typeMap = typeMap) { backstackEntry ->
            val task: NavDestination.Task = backstackEntry.toRoute()
            TaskScreen(
                content = task,
                onBack = { navController.popBackStack() },
            )
        }
    }
}

// Screens
@Serializable
sealed class NavDestination {
    // Without payload
    @Serializable
    object Home : NavDestination()

    // With payload
    @Serializable
    class Task private constructor(val taskKey: ObjectId?) : NavDestination() {
        constructor(task: ToDoTask?) : this(task?.id)
    }
}
