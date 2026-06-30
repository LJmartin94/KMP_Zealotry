package presentation.example

import data.example.ExampleRepository
import toad.ActionDependencies

data class ExampleActionDependencies(
    val exampleRepository: ExampleRepository,
) : ActionDependencies()
