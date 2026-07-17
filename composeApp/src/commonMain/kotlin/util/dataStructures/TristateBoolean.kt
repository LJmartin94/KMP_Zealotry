@file:Suppress("TooManyFunctions")

package util.dataStructures

/**
 *  A check if value is true or undefined
 *  Definitely not false.
 *
 *  T = T
 *  F = F
 *  ? = T
 */
fun Boolean?.isNotFalse(): Boolean = (this == null) || this

fun Boolean?.isTrueOrNull(): Boolean = this.isNotFalse()

/**
 *  A check if value is false or undefined
 *  Definitely not true.
 *
 *  T = F
 *  F = T
 *  ? = T
 */
fun Boolean?.isNotTrue(): Boolean = (this == null) || !this

fun Boolean?.isFalsey(): Boolean = this.isNotTrue()

fun Boolean?.isFalseOrNull(): Boolean = this.isNotTrue()

/**
 * A check if the value is true & defined.
 * Definitely true.
 * The logical 'true' where false or null == false
 *
 * T = T
 * F = F
 * ? = F
 */
fun Boolean?.isTrue(): Boolean = (this != null) && this

fun Boolean?.isTruthy(): Boolean = this.isTrue()

/**
 * A check if the value is false & defined.
 * Definitely false.
 *
 * T = F
 * F = T
 * ? = F
 */
fun Boolean?.isFalse(): Boolean = (this != null) && !this

/**
 * A check if the value has been set.
 * Has a Boolean value.
 *
 * T = T
 * F = T
 * ? = F
 */
fun Boolean?.isNotNull(): Boolean = this != null

fun Boolean?.isTrueOrFalse(): Boolean = this.isNotNull()

fun Boolean?.isDefined(): Boolean = this.isNotNull()

/**
 * A check if the value has not been set.
 * Definitely null/undefined.
 *
 * T = F
 * F = F
 * ? = T
 */
fun Boolean?.isNull(): Boolean = this == null

fun Boolean?.isUndefined(): Boolean = this.isNull()
