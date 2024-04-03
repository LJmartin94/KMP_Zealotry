package presentation.screens.mainMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainMenuButton(
    modifier: Modifier = Modifier,
    navOp: () -> Unit,
    textRes: StringResource,
    backgroundRes: DrawableResource
) =
    Box(
        modifier = modifier.clickable { navOp() }
            .paint(
                painterResource(backgroundRes),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Text(
            text = stringResource(textRes),
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }