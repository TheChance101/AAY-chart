package com.aay.compose.lineChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.utils.clickedOnThisPoint
import com.aay.compose.utils.formatToThousandsMillionsBillions

private var lastClickedPoint: Pair<Float, Float>? = null

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawQuarticLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisData: List<String>,
    spacingX: Dp,
    spacingY: Dp,
    specialChart: Boolean,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasurer: TextMeasurer,
) {

    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(xAxisData.first().toString()),
    ).size.width

    val startSpace = (spacingX) + (textLayoutResult / 2).dp
    val spaceBetweenXes = ((size.width - startSpace.toPx()) / (xAxisData.size - 1)).toDp()

    val strokePathOfQuadraticLine = drawLineAsQuadratic(
        line = line,
        lowerValue = lowerValue,
        upperValue = upperValue,
        animatedProgress = animatedProgress,
        spacingX = spacingX,
        spacingY = spacingY,
        specialChart = specialChart,
        clickedPoints = clickedPoints,
        textMeasurer = textMeasurer,
        xAxisData = xAxisData
    )

    if (line.lineShadow && !specialChart) {
        val fillPath = strokePathOfQuadraticLine.apply {
            lineTo(size.width - spaceBetweenXes.toPx() + 40.dp.toPx(), size.height * 40)
            lineTo(spacingX.toPx() * 2, size.height * 40)
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
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingX: Dp,
    spacingY: Dp,
    specialChart: Boolean,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasurer: TextMeasurer,
    xAxisData: List<String>,
) = Path().apply {
    var medX: Float
    val height = size.height.toDp()

    drawPathLineWrapper(
        lineParameter = line,
        strokePath = this,
        animatedProgress = animatedProgress,
    ) { lineParameter, index ->

        val textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(xAxisData[index]),
        ).size.width

        val yTextLayoutResult = textMeasurer.measure(
            text = AnnotatedString(upperValue.formatToThousandsMillionsBillions()),
        ).size.width

        val startSpace = (spacingX) + (textLayoutResult / 2).dp
        val spaceBetweenXes = ((size.width - startSpace.toPx()) / (xAxisData.size - 1)).toDp()

        val info = lineParameter.data[index]
        val nextInfo = lineParameter.data.getOrNull(index + 1) ?: lineParameter.data.last()
        val firstRatio = (info - lowerValue) / (upperValue - lowerValue)
        val secondRatio = (nextInfo - lowerValue) / (upperValue - lowerValue)

        val xFirstPoint = (yTextLayoutResult.toDp()) + index * spaceBetweenXes
        val xSecondPoint =
            (yTextLayoutResult.toDp() ) + (index + checkLastIndex(lineParameter.data, index)) * spaceBetweenXes

        val yFirstPoint = (height.toPx()
                + 11.dp.toPx()
                - spacingY.toPx()
                - (firstRatio * (size.height.toDp() - spacingY).toPx())
                )
        val ySecondPoint = (height.toPx()
                + 11.dp.toPx()
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
                circleWithRectAndText(
                    x = xFirstPoint,
                    y = yFirstPoint,
                    textMeasure = textMeasurer,
                    info = info,
                    stroke = Stroke(width = 2.dp.toPx()),
                    line = line,
                    animatedProgress = animatedProgress
                )
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
