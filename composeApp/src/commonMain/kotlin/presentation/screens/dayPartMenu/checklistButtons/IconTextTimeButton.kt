package presentation.screens.dayPartMenu.checklistButtons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.reusableUi.ChiaroscuroDrawable
import presentation.style.ColourCompositionLocal
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.empty

@OptIn(ExperimentalResourceApi::class)
@Composable
fun IconTextTimeButton(
    chiaro: ChiaroscuroDrawable,
    textRes: StringResource,
    timeRes: StringResource,
    buttonHeight: Int = 50,
    buttonModifier: Modifier = Modifier.background(ColourCompositionLocal.current.primaryVariant).fillMaxWidth(1f),
    verticalAlign: Alignment.Vertical = Alignment.CenterVertically,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit,
) = Row(
    modifier =
        buttonModifier.clickable(
            onClick = onClick,
        ).padding(8.dp).defaultMinSize(minHeight = buttonHeight.dp).minimumInteractiveComponentSize(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = verticalAlign,
) {
    Image(
        modifier = Modifier.defaultMinSize(minHeight = buttonHeight.dp).minimumInteractiveComponentSize(),
        painter = painterResource(chiaro.getDrawable()),
        contentScale = ContentScale.FillHeight,
        contentDescription = null,
    )
    Text(
        textAlign = textAlign,
        text = stringResource(textRes),
        fontSize = LocalTextStyle.current.fontSize,
    )
    Text(
        textAlign = textAlign,
        text = stringResource(timeRes),
    )
}

@OptIn(ExperimentalResourceApi::class)
data class IconTextTimeBundle(
    val chiaro: ChiaroscuroDrawable,
    val textRes: StringResource = Res.string.empty,
    val timeRes: StringResource = Res.string.empty,
    val onClick: () -> Unit = {},
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun IconTextTimeButton(
    bundle: IconTextTimeBundle,
    buttonModifier: Modifier = Modifier.background(ColourCompositionLocal.current.primaryVariant).fillMaxWidth(1f),
    verticalAlign: Alignment.Vertical = Alignment.CenterVertically,
    textAlign: TextAlign = TextAlign.Center,
) {
    IconTextTimeButton(
        chiaro = bundle.chiaro,
        textRes = bundle.textRes,
        timeRes = bundle.timeRes,
        buttonModifier = buttonModifier,
        verticalAlign = verticalAlign,
        textAlign = textAlign,
        onClick = bundle.onClick,
    )
}
