package presentation.reusableUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Scope for [AdaptiveColumn]'s content, exposing [adaptiveWeight] as a stand-in for
 * [ColumnScope.weight]/[RowScope.weight], and [adaptiveFillCrossAxis] as a stand-in for
 * whichever of [Modifier.fillMaxWidth]/[Modifier.fillMaxHeight] fills the axis [AdaptiveColumn]
 * *isn't* laying children out along — whichever actually matches the orientation it rendered with.
 */
interface AdaptiveScope {
    fun Modifier.adaptiveWeight(
        weight: Float,
        fill: Boolean = true,
    ): Modifier

    fun Modifier.adaptiveFillCrossAxis(): Modifier
}

private class ColumnAdaptiveScope(
    private val scope: ColumnScope,
) : AdaptiveScope {
    override fun Modifier.adaptiveWeight(
        weight: Float,
        fill: Boolean,
    ): Modifier = with(scope) { this@adaptiveWeight.weight(weight, fill) }

    override fun Modifier.adaptiveFillCrossAxis(): Modifier = this@adaptiveFillCrossAxis.fillMaxWidth()
}

private class RowAdaptiveScope(
    private val scope: RowScope,
) : AdaptiveScope {
    override fun Modifier.adaptiveWeight(
        weight: Float,
        fill: Boolean,
    ): Modifier = with(scope) { this@adaptiveWeight.weight(weight, fill) }

    override fun Modifier.adaptiveFillCrossAxis(): Modifier = this@adaptiveFillCrossAxis.fillMaxHeight()
}

/**
 * UI element that will display as a column in portrait mode, and as row in landscape
 */
@Composable
fun AdaptiveColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable AdaptiveScope.() -> Unit,
) = BoxWithConstraints {
    val portrait: Boolean = this.maxHeight >= this.maxWidth
    when (portrait) {
        true ->
            Column(
                modifier,
                verticalArrangement,
                horizontalAlignment,
            ) { ColumnAdaptiveScope(this).content() }

        false ->
            Row(
                modifier,
                verticalArrangement.flip(),
                horizontalAlignment.flip(),
            ) { RowAdaptiveScope(this).content() }
    }
}

private fun Arrangement.Vertical.flip(): Arrangement.Horizontal =
    when (this) {
        Arrangement.Top -> Arrangement.Start
        Arrangement.Bottom -> Arrangement.End
        else -> this as Arrangement.Horizontal
    }

private fun Alignment.Horizontal.flip(): Alignment.Vertical =
    when (this) {
        Alignment.Start -> Alignment.Top
        Alignment.End -> Alignment.Bottom
        Alignment.CenterHorizontally -> Alignment.CenterVertically
        else -> this as Alignment.Vertical
    }
