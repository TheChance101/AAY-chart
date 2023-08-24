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


fun DrawScope.drawDefaultLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int,
    spacingX: Dp,
    spacingY: Dp,
    clickedPoints: MutableList<Pair<Float, Float>>, // Add this parameter
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
        clickedPoints = clickedPoints, // Pass the clicked points list
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


private fun DrawScope.drawLineAsDefault(
    lineParameter: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    spaceBetweenXes: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingX: Dp,
    spacingY: Dp,
    clickedPoints: MutableList<Pair<Float, Float>>, // Add this parameter
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

        val tolerance = 20.dp.toPx() // Adjust this value as needed
        val clickedOnThisPoint = clickedPoints.any {
            val xDiff = it.first - startXPoint.toPx()
            val yDiff = it.second - startYPoint
            val distance = hypot(xDiff.toDouble(), yDiff)
            distance <= tolerance
        }

        if (index == 0) {
            moveTo(startXPoint.toPx(), startYPoint.toFloat())
            if (clickedOnThisPoint) {
                drawCircle(
                    color = lineParameter.lineColor,
                    radius = 5.dp.toPx(),
                    center = Offset(startXPoint.toPx(), startYPoint.toFloat())
                )
            }
        } else {
            lineTo(startXPoint.toPx(), startYPoint.toFloat())
            if (clickedOnThisPoint) {
                drawCircle(
                    color = lineParameter.lineColor,
                    radius = 5.dp.toPx(),
                    center = Offset(startXPoint.toPx(), startYPoint.toFloat())
                )
            }
        }
    }
}

