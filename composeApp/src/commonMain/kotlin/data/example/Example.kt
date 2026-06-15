package data.example

data class Example (
    val id: String,
    val toggle: Boolean,
){
    val isActive
        get() = toggle

    companion object {
        const val FIRST = "example_one"
        const val SECOND = "example_two"
    }
}