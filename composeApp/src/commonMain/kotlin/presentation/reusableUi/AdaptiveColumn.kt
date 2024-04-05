package presentation.reusableUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


/**
 * Doesn't work, am unable to pass the scope of the modifier at runtime
 */
fun Modifier.adaptiveWeight(float: Float, fill: Boolean = true): Modifier {
    println(this)
    return when (this) {
        is ColumnScope -> Modifier.weight(float, fill)
        is RowScope -> Modifier.weight(float, fill)
        else -> this
    }
}

/**
 * UI element that will display as a column in portrait mode, and as row in landscape
 */
@Composable
fun AdaptiveColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable() (() -> Unit)
) {
    return BoxWithConstraints {
        val portrait: Boolean = this.maxHeight >= this.maxWidth
        when (portrait) {
            true -> Column(
                modifier,
                verticalArrangement,
                horizontalAlignment
            ) { this.apply { content() } }

            false -> Row(
                modifier,
                verticalArrangement.flip(),
                horizontalAlignment.flip()
            ) { content() }
        }
    }
}

private fun Arrangement.Vertical.flip(): Arrangement.Horizontal {
    return when (this) {
        Arrangement.Top -> Arrangement.Start
        Arrangement.Bottom -> Arrangement.End
        else -> this as Arrangement.Horizontal
    }
}

private fun Alignment.Horizontal.flip(): Alignment.Vertical {
    return when (this) {
        Alignment.Start -> Alignment.Top
        Alignment.End -> Alignment.Bottom
        Alignment.CenterHorizontally -> Alignment.CenterVertically
        else -> this as Alignment.Vertical
    }
}