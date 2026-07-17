package data.example

data class Example(
    val id: String,
    val toggle: Boolean,
) {
    val isActive
        get() = toggle

    // At the data layer these are 'seedKeys', but as seeding is strictly a database concern, at the domain level we call them canonicalKeys.

    /**
     * Typed identifiers for pre-seeded rows that are always present in the database.
     *
     * Hierarchically, these rows sit alongside user-created data in the same table — [CanonicalKey]
     * is how the domain layer holds a handle on our pre-seeded data specifically.
     *
     * At the data layer these keys are stored as [seedKey] strings, but seeding is a database-only
     * concern. So at the domain layer we call these [CanonicalKey]s.
     * Using a typed enum rather than plain String constants ensures canonical keys
     * cannot be accidentally passed to methods expecting an entity id, and vice versa.
     */
    enum class CanonicalKey(
        val value: String,
    ) {
        FIRST("example_one"),
        SECOND("example_two"),
    }
}
