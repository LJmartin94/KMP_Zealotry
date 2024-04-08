package presentation.style

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Padding(
    val min: Dp = 1.dp,
    val quart: Dp,
    val half: Dp,
    val standard: Dp,
    val dbl: Dp,
    val quad: Dp,
)

val compactPadding = Padding(
    min = 1.dp,
    quart = 1.5f.dp,
    half = 3.dp,
    standard = 6.dp,
    dbl = 12.dp,
    quad = 24.dp,
)

val mediumPadding = Padding(
    min = 1.dp,
    quart = 3.dp,
    half = 6.dp,
    standard = 12.dp,
    dbl = 24.dp,
    quad = 48.dp,
)

val expandedPadding = Padding(
    min = 1.dp,
    quart = 4.5f.dp,
    half = 9.dp,
    standard = 18.dp,
    dbl = 36.dp,
    quad = 72.dp,
)