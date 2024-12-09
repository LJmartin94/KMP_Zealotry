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
import presentation.reusableUi.Chiaroscuro
import presentation.style.ColourCompositionLocal

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainTaskButton(
    chiaro: Chiaroscuro,
    text: String,
    time: String,
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
        painter = chiaro.getPainter(),
        contentScale = ContentScale.FillHeight,
        contentDescription = null,
    )
    Text(
        textAlign = textAlign,
        text = text,
        fontSize = LocalTextStyle.current.fontSize,
    )
    Text(
        textAlign = textAlign,
        text = time,
    )
}

data class IconTextTimeBundle(
    val chiaro: Chiaroscuro,
    val text: String = "",
    val time: String = "",
    val onClick: () -> Unit = {},
)

@Composable
fun MainTaskButton(
    bundle: IconTextTimeBundle,
    buttonModifier: Modifier = Modifier.background(ColourCompositionLocal.current.primaryVariant).fillMaxWidth(1f),
    verticalAlign: Alignment.Vertical = Alignment.CenterVertically,
    textAlign: TextAlign = TextAlign.Center,
) {
    MainTaskButton(
        chiaro = bundle.chiaro,
        text = bundle.text,
        time = bundle.time,
        buttonModifier = buttonModifier,
        verticalAlign = verticalAlign,
        textAlign = textAlign,
        onClick = bundle.onClick,
    )
}
