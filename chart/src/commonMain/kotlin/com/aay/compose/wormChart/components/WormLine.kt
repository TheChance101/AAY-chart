package com.aay.compose.wormChart.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

fun DrawScope.wormLine(
    data: List<Double>,
    centerY: Float,
    lowerValue: Int,
    upperValue: Int,
    lastX: Float,
    lastY: Float,
) {
    val chartWidth = size.width - 100.dp.toPx()
    val spacePerDataPoint = chartWidth / (data.size - 1)
    val path = Path()

    path.moveTo(size.width, centerY)

    data.indices.forEach { index ->
        val info = data[index]
        val ratio = (info - lowerValue) / (upperValue - lowerValue)

        val x = size.width - (100.dp.toPx() + index * spacePerDataPoint)
        val y = centerY - (ratio * centerY).toFloat()

        val prevX = if (index > 0) {
            size.width - (100.dp.toPx() + (index - 1) * spacePerDataPoint)
        } else {
            size.width
        }

        val prevY = if (index > 0) {
            centerY - ((data[index - 1] - lowerValue) / (upperValue - lowerValue) * centerY).toFloat()
        } else {
            centerY
        }

        val medX = (prevX + x) / 2
        val medY = (prevY + y) / 2

        path.quadraticBezierTo(prevX, prevY, medX, medY)
    }

    path.lineTo(lastX, lastY)

    drawPath(
        path = path,
        color = Color.Red,
        style = Stroke(
            width = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    )
}