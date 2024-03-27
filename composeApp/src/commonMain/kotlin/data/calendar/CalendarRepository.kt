package data.calendar

import androidx.compose.runtime.Composable
import data.timeUtils.getModifiedDay
import data.timeUtils.toCustomString

class CalendarRepository {
    @Composable
    fun getToday() {
        getModifiedDay().toCustomString()
    }
}