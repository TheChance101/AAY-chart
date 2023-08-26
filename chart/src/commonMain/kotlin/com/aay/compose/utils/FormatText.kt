package com.aay.compose.utils

fun Long.formatToThousandsMillionsBillions(): String {
    return when {
        this < 1000 -> "${this.toFloat()}"
        this < 1000000 -> "${String.format("%.1f", this.toFloat() / 1000)}k"
        this < 1000000000 -> "${String.format("%.1f", this.toFloat() / 1000000)}M"
        this < 1000000000000 -> "${String.format("%.1f", this.toFloat() / 1000000000)}B"
        else -> "${String.format("%.1f", this.toFloat() / 1000000000000)}T"
    }
}