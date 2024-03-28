package data.calendar

import kotlinx.datetime.DayOfWeek

class CalendarRepository() {
    fun getToday(): DayOfWeek = getTodayWithOffset()
}