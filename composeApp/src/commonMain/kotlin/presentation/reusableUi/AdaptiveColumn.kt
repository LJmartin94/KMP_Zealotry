package presentation.reusableUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

//class OrientationWeight
//class Orientation

/**
 * UI element that will display as a column in portrait mode, and as row in landscape
 */
@Composable
fun AdaptiveColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    return BoxWithConstraints {
        val portrait: Boolean = this.maxHeight >= this.maxWidth
        when (portrait) {
            true -> Column(modifier, verticalArrangement, horizontalAlignment, content)
            false -> Row(
                modifier,
                verticalArrangement.flip(),
                horizontalAlignment.flip()
            ) { Text("Test") }
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