package presentation.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowSize(): WindowSize {
    val windowInfo: WindowInfo = LocalWindowInfo.current
    val windowSize by remember(windowInfo) { // Keeps one derived state object per window

        // Recalculates if windowInfo changes state, but only notifies readers if outcome changes
        derivedStateOf { windowInfo.containerDpSize.toWindowSize() }
    }
    return windowSize
}

fun DpSize.toWindowSize(): WindowSize {
    val lowestVal = minOf(width, height)
    return when {
        lowestVal < 400.dp -> WindowSize.COMPACT
        lowestVal > 840.dp -> WindowSize.EXPANDED
        else -> WindowSize.MEDIUM
    }
}

// Ordering from smallest to largest is important as it allows commonsense comparisons,
// e.g. >= MEDIUM
enum class WindowSize {
    COMPACT,
    MEDIUM,
    EXPANDED,
}