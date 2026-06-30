package data

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/** Generates a new RFC-4122 UUID v4 string, compatible with all KMP targets. */
@OptIn(ExperimentalUuidApi::class)
fun generateEntityId(): String = Uuid.random().toString()
