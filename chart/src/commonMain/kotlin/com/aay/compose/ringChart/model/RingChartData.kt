package com.aay.compose.ringChart.model

import androidx.compose.ui.graphics.Color

/**
 * Data class representing a segment of a ring chart.
 *
 * @param value The current value of the metric.
 * @param maxValue The maximum value or goal for the metric.
 * @param color The color of the ring.
 * @param name The name of the metric.
 */
data class RingChartData(
    val value: Double,
    val maxValue: Double,
    val color: Color,
    val name: String
)