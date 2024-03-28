package data.calendar

import data.timeUtils.getModifiedDay
import kotlinx.datetime.DayOfWeek

class CalendarRepository() {
    fun getToday(): DayOfWeek = getModifiedDay()
}