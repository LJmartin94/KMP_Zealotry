package data.example

import data.example.source.local.ExampleEntityLocal

fun Example.toLocal(): ExampleEntityLocal {
    return ExampleEntityLocal(
        id = this.id,
        toggle = this.toggle,
    )
}

fun List<Example>.toLocal() = map(Example::toLocal)

fun ExampleEntityLocal.toExternal() =
    Example(
        id = id,
        toggle = toggle,
    )

fun List<ExampleEntityLocal>.toExternal() = map(ExampleEntityLocal::toExternal)
