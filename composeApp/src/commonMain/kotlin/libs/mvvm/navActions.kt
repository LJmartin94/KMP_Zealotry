package libs.mvvm

import tutorial.NavDestination

typealias NavigateToAction = (destination: NavDestination) -> Unit
typealias NavigateBackAction = () -> Unit
