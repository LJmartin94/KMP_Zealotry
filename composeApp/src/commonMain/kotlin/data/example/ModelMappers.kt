package data.example

import data.example.source.local.ExampleEntityLocal
import org.mongodb.kbson.ObjectId
import kotlin.jvm.JvmName

fun Example.toLocal(): ExampleEntityLocal {
    return ExampleEntityLocal().apply {
        id = ObjectId(this@toLocal.id)
        toggle = this@toLocal.toggle
    }
}

fun List<Example>.toLocal() = map(Example::toLocal)

fun ExampleEntityLocal.toExternal() = Example(
        id = id.toHexString(),
        toggle = toggle,
    )

fun List<ExampleEntityLocal>.toExternal() = map(ExampleEntityLocal::toExternal)