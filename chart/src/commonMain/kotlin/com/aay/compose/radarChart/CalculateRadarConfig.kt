package com.aay.compose.radarChart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.aay.compose.radarChart.model.RadarChartConfig
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal fun calculateRadarConfig(
    labelRadius: Float,
    netRadius: Float,
    size: Size,
    numLines: Int,
    scalarSteps: Int
): RadarChartConfig {

    val netCornersPoints = mutableListOf<Offset>()
    val stepsStartPoints = mutableListOf<Offset>()
    val stepsEndPoints = mutableListOf<Offset>()
    val polygonPoints = mutableListOf<Offset>()
    val labelsPoints = mutableListOf<Offset>()

    val center = Offset(size.width / 2, size.height / 2)
    val angleBetweenLines = 2 * PI / numLines
    val angleOfFirstLine = 0 * angleBetweenLines
    val offsetAngle = -PI / 2 - angleOfFirstLine

    for (lineIndex in 0 until numLines) {
        val angle = lineIndex * angleBetweenLines + offsetAngle
        val nextIndex = (lineIndex + 1) % numLines
        val nextAngle = nextIndex * angleBetweenLines + offsetAngle
        val value = netRadius / scalarSteps
        val endPoint = getCircumferencePointOffset(center, netRadius, angle)
        netCornersPoints.add(endPoint)

        val labelEndPoint = getCircumferencePointOffset(center, labelRadius, angle)
        labelsPoints.add(labelEndPoint)

        for (step in 1 until scalarSteps + 1) {
            val startEndPoint = getCircumferencePointOffset(center, value * step, angle)
            val nextEndPoint = getCircumferencePointOffset(center, value * step, nextAngle)
            stepsStartPoints.add(startEndPoint)
            stepsEndPoints.add(nextEndPoint)
            if (lineIndex == 0) polygonPoints.add(startEndPoint)
        }
    }

    return RadarChartConfig(
        center, netCornersPoints, stepsEndPoints, stepsStartPoints, polygonPoints, labelsPoints
    )
}

private fun getCircumferencePointOffset(
    center: Offset,
    radius: Float,
    angle: Double
) = Offset(
    center.x + radius * cos(angle).toFloat(),
    center.y + radius * sin(angle).toFloat()
)


internal fun getPolygonShapeEndPoints(
    values: List<Double>,
    radius: Float,
    scalarValue: Double,
    center: Offset,
    scalarSteps: Int
): List<Offset> {
    val scalarShapeEndPoints = mutableListOf<Offset>()
    val angleBetweenLines = 2 * PI / values.size

    for (index in values.indices) {
        val angleOfFirstLine = 0 * angleBetweenLines
        val offsetAngle = -PI / 2 - angleOfFirstLine
        val angle = index * angleBetweenLines + offsetAngle
        val polygonRadius = radius - (radius / scalarSteps)

        val value = values[index]
        val scalarRadius = (value / scalarValue) * polygonRadius
        val scalarShapeEndPoint = getCircumferencePointOffset(center, scalarRadius.toFloat(), angle)
        scalarShapeEndPoints.add(scalarShapeEndPoint)
    }

    return scalarShapeEndPoints
}







