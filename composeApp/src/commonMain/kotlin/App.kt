import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.MainTheme

import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.compose_multiplatform

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MainTheme {
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Today is ${getModifiedDay()}",
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                Column(
                    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

/**
 * Gets current day, or previous day if before 4am.
 */
fun getModifiedDay(): DayOfWeek {
    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    val localTime = now.toLocalDateTime(zone)
    val today = localTime.dayOfWeek.isoDayNumber // Mon: 1 .. Sun: 7
    val yesterday = sanitiseIsoDay(today - 1)
    return when {
        localTime.hour >= 4 -> DayOfWeek(today)
        else -> DayOfWeek(yesterday)
    }
}

/**
 * Map any Int to a value in 1 .. 7, consistent with kotlinx.datetime isoDayNumber.
 * E.g.:
 * -6 == 1 == 8 == Monday
 * -1 == 6 == 13 == Saturday
 *  0 == 7 == 14 == Sunday
 */
private fun sanitiseIsoDay(rawIso: Int): Int {
    var sanitisedIso = rawIso
    if (sanitisedIso > 7) sanitisedIso %= 7
    while (sanitisedIso < 1) sanitisedIso += 7
    return sanitisedIso
}