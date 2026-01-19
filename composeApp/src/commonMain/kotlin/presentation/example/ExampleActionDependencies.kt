package presentation.example

import data.example.ExampleRepository
import kotlinx.coroutines.CoroutineScope
import toad.ActionDependencies

data class ExampleActionDependencies (
    val exampleRepository: ExampleRepository,
    override val coroutineScope: CoroutineScope
): ActionDependencies()
