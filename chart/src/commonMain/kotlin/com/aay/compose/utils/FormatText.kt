package com.aay.compose.utils

import java.math.BigDecimal

internal fun Float.formatToThousandsMillionsBillions(): String {
    val value = BigDecimal(this.toDouble())
    val absValue = value.abs()
    return when {
        absValue < BigDecimal.valueOf(1000) -> String.format("%.1f", value)
        absValue < BigDecimal.valueOf(1000000) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1000)))}k"
        absValue < BigDecimal.valueOf(1000000000) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1000000)))}M"
        absValue < BigDecimal.valueOf(1000000000000) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1000000000)))}B"
        absValue < BigDecimal.valueOf(1000000000000000) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1000000000000)))}T"
        absValue < BigDecimal.valueOf(1e18) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1e15)))}Q"
        absValue < BigDecimal.valueOf(1e21) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1e18)))}KQ"
        absValue < BigDecimal.valueOf(1e24) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1e21)))}MQ"
        absValue < BigDecimal.valueOf(1e27) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1e24)))}BQ"
        absValue < BigDecimal.valueOf(1e30) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1e27)))}TQ"
        absValue < BigDecimal.valueOf(1e33) -> "${String.format("%.1f", value.divide(BigDecimal.valueOf(1e30)))}QQ"
        else -> "Infinity"
    }
}
