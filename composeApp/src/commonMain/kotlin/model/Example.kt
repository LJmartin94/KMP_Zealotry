package model

data class Example(
    val id: String,
    val toggle: Boolean,
) {
    val isActive
        get() = toggle

    // At the data layer these are 'seedKeys', but as seeding is strictly a database concern,
    // within the rest of the app we call them canonicalKeys.

    /**
     * Typed identifiers for pre-seeded rows that are always present in the database.
     *
     * Hierarchically, these rows sit alongside user-created data in the same table — [CanonicalKey]
     * is how the rest of the app holds a handle on our pre-seeded data specifically.
     *
     * A key is only an identity. How it is stored (the seed key string in the database) is a
     * data-layer concern, mapped in `data.example.toSeedKey`.
     * Using a typed enum rather than plain String constants ensures canonical keys
     * cannot be accidentally passed to methods expecting an entity id, and vice versa.
     */
    enum class CanonicalKey {
        FIRST,
        SECOND,
    }
}
