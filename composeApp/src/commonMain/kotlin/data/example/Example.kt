package data.example

import data.SeedKey

data class Example (
    val id: String,
    val toggle: Boolean,
){
    val isActive
        get() = toggle

    companion object {
        val SEED_EXAMPLE_ONE = SeedKey("example_one")
    }
}