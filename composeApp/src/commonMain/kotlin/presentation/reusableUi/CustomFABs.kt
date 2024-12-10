package presentation.reusableUi

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun CustomExtendedFAB(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colors.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    contentAlignment: Alignment = Alignment.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    CustomFAB(
        modifier = modifier.sizeIn(
            minWidth = ExtendedFabSize,
            minHeight = ExtendedFabSize
        ),
        onClick = onClick,
        interactionSource = interactionSource,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        contentAlignment = contentAlignment,
    ) {
        val startPadding = if (icon == null) ExtendedFabTextPadding else ExtendedFabIconPadding
        Row(
            modifier = Modifier.padding(
                start = startPadding,
                end = ExtendedFabTextPadding
            ),
            verticalAlignment = verticalAlignment
        ) {
            if (icon != null) {
                icon()
                Spacer(Modifier.width(ExtendedFabIconPadding))
            }
            text()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colors.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation.elevation(interactionSource).value,
        interactionSource = interactionSource,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(MaterialTheme.typography.button) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = FabSize, minHeight = FabSize),
                    contentAlignment = contentAlignment,
                ) { content() }
            }
        }
    }
}

private val FabSize = 56.dp
private val ExtendedFabSize = 48.dp
private val ExtendedFabIconPadding = 12.dp
private val ExtendedFabTextPadding = 20.dp