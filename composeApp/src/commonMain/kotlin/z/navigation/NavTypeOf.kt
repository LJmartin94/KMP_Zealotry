package z.navigation

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> navTypeOf(isNullableAllowed: Boolean = true) =
    object : NavType<T>(isNullableAllowed = isNullableAllowed) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): T? {
            return Json.Default.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: T,
        ) {
            bundle.putString(key, Json.Default.encodeToString(value))
        }

        override fun parseValue(value: String): T {
            return Json.Default.decodeFromString(value)
        }

        override fun serializeAsValue(value: T): String {
            return Json.Default.encodeToString(value)
        }
    }
