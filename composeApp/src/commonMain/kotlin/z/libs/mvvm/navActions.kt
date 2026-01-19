package z.libs.mvvm

import z.navigation.tutorial.NavDestination

typealias NavigateToAction = (destination: NavDestination) -> Unit
typealias NavigateBackAction = () -> Unit
