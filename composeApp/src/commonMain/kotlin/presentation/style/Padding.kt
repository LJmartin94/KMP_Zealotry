package presentation.style

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val PaddingCompositionLocal = staticCompositionLocalOf { mediumPadding }

class Padding(
    val min: Dp = 1.dp,
    val quart: Dp,
    val half: Dp,
    val standard: Dp,
    val dbl: Dp,
    val quad: Dp,
)

fun WindowSize.padding(): Padding = when (this){
    WindowSize.COMPACT -> compactPadding
    WindowSize.MEDIUM -> mediumPadding
    WindowSize.EXPANDED -> expandedPadding
}

val compactPadding =
    Padding(
        min = 1.dp,
        quart = 1.5f.dp,
        half = 3.dp,
        standard = 6.dp,
        dbl = 12.dp,
        quad = 24.dp,
    )

val mediumPadding =
    Padding(
        min = 1.dp,
        quart = 3.dp,
        half = 6.dp,
        standard = 12.dp,
        dbl = 24.dp,
        quad = 48.dp,
    )

val expandedPadding =
    Padding(
        min = 1.dp,
        quart = 4.5f.dp,
        half = 9.dp,
        standard = 18.dp,
        dbl = 36.dp,
        quad = 72.dp,
    )




//enum class Padding {
//    MIN,
//    QUART,
//    HALF,
//    STANDARD,
//    DBL,
//    QUAD,
//}
//
//fun WindowSize.dp(padding: Padding): Dp{
//    return when(this){
//        WindowSize.COMPACT -> compactPadding(padding)
//        WindowSize.MEDIUM -> mediumPadding(padding)
//        WindowSize.EXPANDED -> expandedPadding(padding)
//    }
//}
//
//fun compactPadding(padding: Padding): Dp {
//    return when (padding) {
//        Padding.MIN -> 1.dp
//        Padding.QUART -> 1.5f.dp
//        Padding.HALF -> 3.dp
//        Padding.STANDARD -> 6.dp
//        Padding.DBL -> 12.dp
//        Padding.QUAD -> 24.dp
//    }
//}
//
//fun mediumPadding(padding: Padding): Dp {
//    return when (padding) {
//        Padding.MIN -> 1.dp
//        Padding.QUART -> 3.dp
//        Padding.HALF -> 6.dp
//        Padding.STANDARD -> 12.dp
//        Padding.DBL -> 24.dp
//        Padding.QUAD -> 48.dp
//    }
//}
//
//fun expandedPadding(padding: Padding): Dp {
//    return when (padding) {
//        Padding.MIN -> 1.dp
//        Padding.QUART -> 4.5f.dp
//        Padding.HALF -> 9.dp
//        Padding.STANDARD -> 18.dp
//        Padding.DBL -> 36.dp
//        Padding.QUAD -> 72.dp
//    }
//}
