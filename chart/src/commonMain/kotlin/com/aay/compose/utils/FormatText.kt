package com.aay.compose.utils

import kotlin.math.abs

fun Float.format(status:String): String {
    val intValue = this.toInt()
    val floatValue = this - intValue
        val decimalPart = (floatValue * 10).toInt()
        return "$intValue.$decimalPart$status"
}

internal fun Float.formatToThousandsMillionsBillions(): String {
    val absValue = abs(this)
    return when {
        absValue < 1000 -> this.format("")
        absValue < 1_000_000 -> (this / 1_000).format("K")
        absValue < 1_000_000_000 -> (this / 1_000_000).format("M")
        absValue < 1_000_000_000_000 -> (this / 1_000_000_000).format("B")
        else -> "Infinity"
    }
}

