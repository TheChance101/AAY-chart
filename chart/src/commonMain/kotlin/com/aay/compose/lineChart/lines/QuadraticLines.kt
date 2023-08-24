package com.aay.compose.lineChart.lines

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.aay.compose.lineChart.model.LineParameters
import kotlin.math.hypot

fun DrawScope.drawQuarticLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int,
    spacingX: Dp,
    spacingY: Dp,
    specialChart: Boolean,
    clickedPoints: MutableList<Pair<Float, Float>>, // Add this parameter
) {

    val spaceBetweenXes = (size.width.toDp() - spacingX) / xAxisSize
    val strokePathOfQuadraticLine = drawLineAsQuadratic(
        line = line,
        lowerValue = lowerValue,
        upperValue = upperValue,
        spaceBetweenXes = spaceBetweenXes,
        animatedProgress = animatedProgress,
        spacingX = spacingX,
        spacingY = spacingY,
        specialChart = specialChart,
        clickedPoints = clickedPoints, // Pass the clicked points list
    )

    if (line.lineShadow && !specialChart) {
        val fillPath = strokePathOfQuadraticLine.apply {
            lineTo(size.width - spaceBetweenXes.toPx() + 32.dp.toPx(), size.height)
            lineTo(spacingX.toPx() + 25.dp.toPx(), size.height)
            close()
        }
        clipRect(right = size.width * animatedProgress.value) {
            drawPath(
                path = fillPath, brush = Brush.verticalGradient(
                    colors = listOf(
                        line.lineColor.copy(alpha = .3f), Color.Transparent
                    ), endY = (size.height.toDp() - spacingY).toPx()
                )
            )
        }
    }
}

private fun DrawScope.drawLineAsQuadratic(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    spaceBetweenXes: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingX: Dp,
    spacingY: Dp,
    specialChart: Boolean,
    clickedPoints: MutableList<Pair<Float, Float>>, // Add this parameter
) = Path().apply {
    var medX: Float
    val height = size.height.toDp()
    drawPathLineWrapper(
        lineParameter = line,
        strokePath = this,
        animatedProgress = animatedProgress,
    ) { lineParameter, index ->

        val info = lineParameter.data[index]
        val nextInfo = lineParameter.data.getOrNull(index + 1) ?: lineParameter.data.last()
        val firstRatio = (info - lowerValue) / (upperValue - lowerValue)
        val secondRatio = (nextInfo - lowerValue) / (upperValue - lowerValue)

        val xFirstPoint = (spacingX + 50.dp / 0.8.dp.toPx()) + index * spaceBetweenXes
        val xSecondPoint = (spacingX + 50.dp / 0.8.dp.toPx()) + (
                index + checkLastIndex(lineParameter.data, index)
                ) * spaceBetweenXes

        val yFirstPoint = (height.toPx()
                + 5.dp.toPx()
                - spacingY.toPx()
                - (firstRatio * (size.height.toDp() - spacingY).toPx())
                )
        val ySecondPoint = (height.toPx()
                + 5.dp.toPx()
                - spacingY.toPx()
                - (secondRatio * (size.height.toDp() - spacingY).toPx())
                )

        val tolerance = 20.dp.toPx() // Adjust this value as needed
        val clickedOnThisPoint = clickedPoints.any {
            val xDiff = it.first - xFirstPoint.toPx()
            val yDiff = it.second - yFirstPoint
            val distance = hypot(xDiff.toDouble(), yDiff)
            distance <= tolerance

        }


        if (index == 0) {
            moveTo(xFirstPoint.toPx(), yFirstPoint.toFloat())
            medX = ((xFirstPoint + xSecondPoint) / 2f).toPx()
            cubicTo(
                medX,
                yFirstPoint.toFloat(),
                medX,
                ySecondPoint.toFloat(),
                xSecondPoint.toPx(),
                ySecondPoint.toFloat()
            )
            if (clickedOnThisPoint) {
                drawCircle(
                    color = line.lineColor,
                    radius = 5.dp.toPx(),
                    center = Offset(xFirstPoint.toPx(), yFirstPoint.toFloat())
                )
            }

        } else {
            medX = ((xFirstPoint + xSecondPoint) / 2f).toPx()
            cubicTo(
                medX,
                yFirstPoint.toFloat(),
                medX,
                ySecondPoint.toFloat(),
                xSecondPoint.toPx(),
                ySecondPoint.toFloat()
            )
            if (clickedOnThisPoint) {
                drawCircle(
                    color = line.lineColor,
                    radius = 5.dp.toPx(),
                    center = Offset(xFirstPoint.toPx(), yFirstPoint.toFloat())
                )
            }
        }
        if (index == 0 && specialChart) {
            chartCircle(xFirstPoint.toPx(), yFirstPoint.toFloat(), color = lineParameter.lineColor, animatedProgress)
        }
        println(clickedOnThisPoint)
    }

}

private fun checkLastIndex(data: List<Double>, index: Int): Int {
    return if (data[index] == data[data.lastIndex])
        0
    else
        1
}
