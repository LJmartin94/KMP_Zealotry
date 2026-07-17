package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import presentation.dayPartMenu.DayPartMenuScreen
import presentation.mainMenu.MainMenuScreen
import kotlin.reflect.typeOf
import data.dayPartMenu.DayPart as DayPartEnum

@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    // Define how to navigate to a route, now we can only navigate to NavDestination type
    val navigateTo = remember { { destination: NavDestination -> navController.navigate(route = destination) } }
    // Define how to pass keys around for loading items from db: avoid passing the objects themselves
    val typeMap =
        mapOf(
            typeOf<DayPartEnum>() to navTypeOf<DayPartEnum>(),
        )

    NavHost(navController = navController, startDestination = NavDestination.MainMenu) {
        composable<NavDestination.MainMenu> {
            MainMenuScreen(
                onNavigate = { route -> navigateTo(route) },
            )
        }

        // TODO: Making every daypart a separate instance of the same screen may be a premature abstraction
        // Probably does make sense: Underlying data structure for daypart will be one db entry -
        // this allows easily moving of tasks between day parts, displaying different routines on workdays/rest days
        composable<NavDestination.DayPart> (typeMap = typeMap) { backstackEntry ->
            val part: NavDestination.DayPart = backstackEntry.toRoute()
            DayPartMenuScreen(
                content = part,
                onBack = { navController.popBackStack() },
            )
        }
    }
}

// Screens
@Serializable
sealed class NavDestination {
    // Without destination arguments
    @Serializable
    object MainMenu : NavDestination()

    // With destination arguments - to display specific content
    @Serializable
    class DayPart private constructor(
        val part: DayPartEnum,
        val working: Boolean,
    ) : NavDestination() {
        constructor(part: DayPartEnum) : this(part, true)
    }

    // @Serializable
    // class Task private constructor(val taskKey: ObjectId?) : NavDestination() {
    //    constructor(task: ToDoTask?) : this(task?.id)
    // }
}
