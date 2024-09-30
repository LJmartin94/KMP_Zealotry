package presentation.screens.mainMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import presentation.style.ColourCompositionLocal

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainMenuSub(
    title: StringResource,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content:
        @Composable()
        (() -> Unit),
) {
    // Top level for sub menu
    return Column(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        // Title
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .weight(4f)
                    .background(ColourCompositionLocal.current.primaryVariant.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = stringResource(title), color = ColourCompositionLocal.current.onPrimary)
        }

        // Container
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .weight(17f)
                    .background(ColourCompositionLocal.current.background.copy(alpha = 0.8f))
                    .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            content()
        }
    }
}
