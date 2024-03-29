package data.screens.dayPartMenu

enum class DayPart {
    MORNING,
    MIDDAY,
    EVENING
}

data class DayPartMenuUIState(val part: DayPart = DayPart.MORNING)