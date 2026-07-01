@file:Suppress("TooManyFunctions")

package z.libs.tristateBool

/**
 *  A check if value is true or undefined
 *  Definitely not false.
 *
 *  T = T
 *  F = F
 *  ? = T
 */
fun Boolean?.isNotFalse(): Boolean {
    return (this == null) || this
}

fun Boolean?.isTrueOrNull(): Boolean {
    return this.isNotFalse()
}

/**
 *  A check if value is false or undefined
 *  Definitely not true.
 *
 *  T = F
 *  F = T
 *  ? = T
 */
fun Boolean?.isNotTrue(): Boolean {
    return (this == null) || !this
}

fun Boolean?.isFalsey(): Boolean {
    return this.isNotTrue()
}

fun Boolean?.isFalseOrNull(): Boolean {
    return this.isNotTrue()
}

/**
 * A check if the value is true & defined.
 * Definitely true.
 * The logical 'true' where false or null == false
 *
 * T = T
 * F = F
 * ? = F
 */
fun Boolean?.isTrue(): Boolean {
    return (this != null) && this
}

fun Boolean?.isTruthy(): Boolean {
    return this.isTrue()
}

/**
 * A check if the value is false & defined.
 * Definitely false.
 *
 * T = F
 * F = T
 * ? = F
 */
fun Boolean?.isFalse(): Boolean {
    return (this != null) && !this
}

/**
 * A check if the value has been set.
 * Has a Boolean value.
 *
 * T = T
 * F = T
 * ? = F
 */
fun Boolean?.isNotNull(): Boolean {
    return this != null
}

fun Boolean?.isTrueOrFalse(): Boolean {
    return this.isNotNull()
}

fun Boolean?.isDefined(): Boolean {
    return this.isNotNull()
}

/**
 * A check if the value has not been set.
 * Definitely null/undefined.
 *
 * T = F
 * F = F
 * ? = T
 */
fun Boolean?.isNull(): Boolean {
    return this == null
}

fun Boolean?.isUndefined(): Boolean {
    return this.isNull()
}
