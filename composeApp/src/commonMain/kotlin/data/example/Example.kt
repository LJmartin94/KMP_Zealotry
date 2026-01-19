package data.example

data class Example (
    val id: String,
    val toggle: Boolean,
){
    val isActive
        get() = toggle
}