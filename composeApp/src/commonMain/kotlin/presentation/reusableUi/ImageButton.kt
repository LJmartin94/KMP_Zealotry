package presentation.reusableUi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageButton(
    imgModifier: Modifier = Modifier,
    imgRes: DrawableResource,
    imgAlpha: Float = DefaultAlpha,
    textModifier: Modifier = Modifier,
    textRes: StringResource,
    onClick: () -> Unit,
) =
    Box(
        modifier = imgModifier.clickable { onClick() }
            .paint(
                painterResource(imgRes),
                alpha = imgAlpha,
                contentScale = ContentScale.FillBounds
            )
    ) {
        Text(
            text = stringResource(textRes),
            modifier = textModifier.align(Alignment.BottomCenter)
        )
    }