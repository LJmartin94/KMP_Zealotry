package presentation.reusableUi

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

/**
 * The app-wide [SnackbarHostState], provided once at the root (the top-level `App` composable)
 * so every screen pushes into the same, single, correctly-anchored
 * [androidx.compose.material.SnackbarHost] instead of each owning its own
 * [androidx.compose.material.Scaffold].
 */
val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> {
        error("SnackbarHostState not provided")
    }
