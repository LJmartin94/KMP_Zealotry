// TEMPORARY: deliberate violation proving the "no @Composable in data" rule fires. Delete in phase 2.
package data

import androidx.compose.runtime.Composable

@Composable
fun TemporaryKonsistViolation() {}
