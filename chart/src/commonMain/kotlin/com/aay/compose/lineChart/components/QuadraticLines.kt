package com.aay.compose.lineChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.utils.clickedOnThisPoint

private var lastClickedPoint: Pair<Float, Float>? = null

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawQuarticLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int,
    spacingX: Dp,
    spacingY: Dp,
    specialChart: Boolean,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasurer: TextMeasurer,
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
        clickedPoints = clickedPoints,
        textMeasurer = textMeasurer
    )

    if (line.lineShadow && !specialChart) {
        val fillPath = strokePathOfQuadraticLine.apply {
            lineTo(size.width - spaceBetweenXes.toPx() + 40.dp.toPx(), size.height)
            lineTo(spacingX.toPx() *2, size.height)
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

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawLineAsQuadratic(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    spaceBetweenXes: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingX: Dp,
    spacingY: Dp,
    specialChart: Boolean,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasurer: TextMeasurer,
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

        val tolerance = 20.dp.toPx()
        val savedClicks =
            clickedOnThisPoint(clickedPoints, xFirstPoint.toPx(), yFirstPoint, tolerance)
        if (savedClicks) {
            if (lastClickedPoint != null) {
                clickedPoints.clear()
                lastClickedPoint = null
            } else {
                lastClickedPoint = Pair(xFirstPoint.toPx(), yFirstPoint.toFloat())
                chartCircle(
                    xFirstPoint.toPx(),
                    yFirstPoint.toFloat(),
                    line.lineColor,
                    animatedProgress,
                    Stroke(width = 2.dp.toPx())
                )
                chartRectangleWithText(xFirstPoint, yFirstPoint, line.lineColor, textMeasurer, info)
            }

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
        }

        if (index == 0 && specialChart) {
            chartCircle(
                xFirstPoint.toPx(),
                yFirstPoint.toFloat(),
                color = lineParameter.lineColor,
                animatedProgress = animatedProgress,
            )
        }
    }
}

private fun checkLastIndex(data: List<Double>, index: Int): Int {
    return if (data[index] == data[data.lastIndex])
        0
    else
        1
}