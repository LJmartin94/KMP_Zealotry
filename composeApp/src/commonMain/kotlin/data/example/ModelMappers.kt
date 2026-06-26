package data.example

import data.example.source.local.ExampleEntityLocal
import kotlin.jvm.JvmName

fun Example.toLocal(): ExampleEntityLocal {
    return ExampleEntityLocal(
        id = this.id,
        toggle = this.toggle,
    )
}

fun List<Example>.toLocal() = map(Example::toLocal)

fun ExampleEntityLocal.toExternal() = Example(
    id = id,
    toggle = toggle,
)

fun List<ExampleEntityLocal>.toExternal() = map(ExampleEntityLocal::toExternal)
