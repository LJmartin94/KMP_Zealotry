package data.example

/**
 * Immutable model class for Example.
 */
data class Example (
    val id: String,
    val toggle: Boolean = false,
){
    val isActive
        get() = toggle
}