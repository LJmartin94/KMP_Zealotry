package model

import kotlinx.serialization.Serializable

@Serializable
enum class DayPart {
    MORNING,
    MIDDAY,
    EVENING,
}