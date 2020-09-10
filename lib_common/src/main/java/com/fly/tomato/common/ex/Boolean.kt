@file:Suppress("unused")

package com.fly.tomato.common.ex

/**
 * Note:
 *
 * @author Sens [2016/7/21]
 */
sealed class BooleanExt<out T> constructor(val boolean: Boolean)

object Otherwise : BooleanExt<Nothing>(true)

class WithData<out T>(val data: T) : BooleanExt<T>(false)

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> = when {
    this -> WithData(block())
    else -> Otherwise
}

inline fun <T> Boolean.no(block: () -> T) = when {
    this -> Otherwise
    else -> WithData(block())
}

inline infix fun <T> BooleanExt<T>.otherwise(block: () -> T): T = when (this) {
    is Otherwise -> block()
    is WithData<T> -> this.data
}

inline fun <T> Boolean.check(yes: () -> T, no: () -> T): T = when {
    this -> yes()
    else -> no()
}

inline operator fun <T> Boolean.invoke(block: () -> T) = yes(block)