
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.style.MainTheme
import presentation.style.setIfDarkMode

@Composable
@Preview
fun App() {
    MainTheme {
        Surface(modifier = Modifier.fillMaxSize().setIfDarkMode()) {
            InitNavStack()
        }
    }
}
