package com.aay.compose.utils

import kotlin.math.hypot

internal fun clickedOnThisPoint(
    clickedPoints: MutableList<Pair<Float, Float>>,
    x: Float,
    y: Double,
    tolerance: Float,
) = clickedPoints.any {
    val xDiff = it.first - x
    val yDiff = it.second - y
    val distance = hypot(xDiff.toDouble(), yDiff)
    distance <= tolerance
}
