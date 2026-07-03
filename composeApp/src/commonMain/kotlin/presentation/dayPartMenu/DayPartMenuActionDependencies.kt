package presentation.dayPartMenu

import data.dayPartMenu.DayPartMenuRepository
import toad.ActionDependencies

data class DayPartMenuActionDependencies(
    val dayPartMenuRepository: DayPartMenuRepository,
) : ActionDependencies()
