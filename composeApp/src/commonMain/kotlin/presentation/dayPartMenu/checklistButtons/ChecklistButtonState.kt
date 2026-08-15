package presentation.dayPartMenu.checklistButtons

import presentation.reusableUi.Chiaroscuro

data class ChecklistButtonState(
    val mainIcon: Chiaroscuro,
    val mainText: String,
    val isActive: Boolean = true,
    val isExpanded: Boolean = false,
    val completeTime: String? = null,
    val durationInSeconds: Number = 0,
    val iterationsMeasured: Number = 0,
    val subtaskList: MutableList<String>,
    val subtasksCompleted: MutableList<String> = mutableListOf(),
    val subtaskIcons: MutableMap<String, Chiaroscuro>,
)
