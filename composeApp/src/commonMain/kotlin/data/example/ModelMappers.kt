package data.example

import data.example.source.local.ExampleEntityLocal
import model.Example

// Storage values for the canonical keys. They are persisted, so never change an existing one
// without a migration. The exhaustive `when` forces a storage value for every new key.
fun Example.CanonicalKey.toSeedKey(): String =
    when (this) {
        Example.CanonicalKey.FIRST -> "example_one"
        Example.CanonicalKey.SECOND -> "example_two"
    }

fun Example.toLocal(): ExampleEntityLocal =
    ExampleEntityLocal(
        id = this.id,
        toggle = this.toggle,
    )

fun List<Example>.toLocal() = map(Example::toLocal)

fun ExampleEntityLocal.toExternal() =
    Example(
        id = id,
        toggle = toggle,
    )

fun List<ExampleEntityLocal>.toExternal() = map(ExampleEntityLocal::toExternal)
