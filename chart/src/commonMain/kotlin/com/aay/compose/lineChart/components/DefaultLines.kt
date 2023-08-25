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
fun DrawScope.drawDefaultLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int,
    spacingX: Dp,
    spacingY: Dp,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasure: TextMeasurer,
) {

    val spaceBetweenXes = (size.width.toDp() - spacingX) / xAxisSize
    val strokePathOfDefaultLine = drawLineAsDefault(
        lineParameter = line,
        lowerValue = lowerValue,
        upperValue = upperValue,
        spaceBetweenXes = spaceBetweenXes,
        animatedProgress = animatedProgress,
        spacingX = spacingX,
        spacingY = spacingY,
        clickedPoints = clickedPoints,
        textMeasure = textMeasure
    )

    if (line.lineShadow) {
        val fillPath = strokePathOfDefaultLine.apply {
            lineTo(size.width - spaceBetweenXes.toPx() + 32.dp.toPx(), size.height)
            lineTo(spacingX.toPx() + 25.dp.toPx(), size.height)
            close()
        }
        clipRect(right = size.width * animatedProgress.value) {
            drawPath(
                path = fillPath, brush = Brush.verticalGradient(
                    colors = listOf(line.lineColor.copy(alpha = .3f), Color.Transparent),
                    endY = (size.height.toDp() - spacingY).toPx() // for shadow height inside line
                )
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawLineAsDefault(
    lineParameter: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    spaceBetweenXes: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingX: Dp,
    spacingY: Dp,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasure: TextMeasurer,
) = Path().apply {

    val height = size.height.toDp()
    drawPathLineWrapper(
        lineParameter = lineParameter,
        strokePath = this,
        animatedProgress = animatedProgress,
    ) { lineParameter, index ->


        val info = lineParameter.data[index]
        val ratio = (info - lowerValue) / (upperValue - lowerValue)
        val startXPoint = (spacingX + 50.dp / 0.8.dp.toPx()) + (index * spaceBetweenXes)
        val startYPoint =
            (height.toPx() + 5.dp.toPx() - spacingY.toPx() - (ratio * (height.toPx() - spacingY.toPx())))

        val tolerance = 20.dp.toPx()
        val savedClicks =
            clickedOnThisPoint(clickedPoints, startXPoint.toPx(), startYPoint, tolerance)


        if (savedClicks) {
            if (lastClickedPoint != null) {
                clickedPoints.clear()
                lastClickedPoint = null
            } else {
                lastClickedPoint = Pair(startXPoint.toPx(), startYPoint.toFloat())
                chartCircle(
                    x = startXPoint.toPx(),
                    y = startYPoint.toFloat(),
                    color = lineParameter.lineColor,
                    animatedProgress = animatedProgress,
                    Stroke(width = 2.dp.toPx())
                )
                chartRectangleWithText(
                    startXPoint,
                    startYPoint,
                    lineParameter.lineColor,
                    textMeasure,
                    info
                )
            }
        }

        if (index == 0) {
            moveTo(startXPoint.toPx(), startYPoint.toFloat())
        } else {
            lineTo(startXPoint.toPx(), startYPoint.toFloat())
        }
    }
}

