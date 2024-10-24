package presentation.reusableUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.style.ColourCompositionLocal

@OptIn(ExperimentalResourceApi::class)
@Composable
fun IconTextTimeButton(
    iconRes: DrawableResource,
    textRes: StringResource,
    timeRes: StringResource,
    buttonModifier: Modifier = Modifier.background(ColourCompositionLocal.current.primaryVariant).fillMaxWidth(1f),
    verticalAlign: Alignment.Vertical = Alignment.CenterVertically,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit,
) = Row(
    modifier = buttonModifier.clickable(onClick = onClick).padding(8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = verticalAlign,
) {
    Image(painterResource(iconRes), null)
    Text(
        textAlign = textAlign,
        text = stringResource(textRes),
    )
    Text(
        textAlign = textAlign,
        text = stringResource(timeRes),
    )
}
