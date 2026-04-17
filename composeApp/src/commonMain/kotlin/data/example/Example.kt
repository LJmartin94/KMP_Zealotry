package data.example

data class Example (
    val id: String,
    val toggle: Boolean,
){
    val isActive
        get() = toggle

    companion object {
        const val SEED_EXAMPLE_ONE = "example_one"
    }
}