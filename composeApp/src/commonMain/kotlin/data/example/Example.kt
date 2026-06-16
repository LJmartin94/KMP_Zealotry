package data.example

data class Example (
    val id: String,
    val toggle: Boolean,
){
    val isActive
        get() = toggle

    // These are the so called 'canonicalKeys' by which we refer to the data we know will be part of the database.
    // Hierarchically the data we specify sits alongside user created data, but we need a handle on how to interact with our data specifically.
    // At the data layer these are 'seedKeys', but as seeding is strictly a database concern, at the domain level we call them canonicalKeys.
    companion object {
        const val FIRST = "example_one"
        const val SECOND = "example_two"
    }
}