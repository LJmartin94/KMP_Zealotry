package z.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> navTypeOf(isNullableAllowed: Boolean = true) =
    object : NavType<T>(isNullableAllowed = isNullableAllowed) {
        override fun get(
            bundle: SavedState,
            key: String,
        ): T? {
            return Json.Default.decodeFromString(bundle.read { getStringOrNull(key) } ?: return null)
        }

        override fun put(
            bundle: SavedState,
            key: String,
            value: T,
        ) {
            bundle.write { putString(key, Json.Default.encodeToString(value)) }
        }

        override fun parseValue(value: String): T {
            return Json.Default.decodeFromString(value)
        }

        override fun serializeAsValue(value: T): String {
            return Json.Default.encodeToString(value)
        }
    }
