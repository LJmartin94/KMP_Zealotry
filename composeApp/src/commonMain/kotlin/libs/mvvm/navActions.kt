package libs.mvvm

import navigation.tutorial.NavDestination

typealias NavigateToAction = (destination: NavDestination) -> Unit
typealias NavigateBackAction = () -> Unit
