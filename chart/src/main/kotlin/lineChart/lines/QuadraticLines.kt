package lineChart.lines

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.*
import drawPathLineWrapper
import lineChart.model.LineParameters
import lineChart.model.LineShadow

fun DrawScope.drawQuarticLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int,
    spacingX:Dp,
    spacingY:Dp,
) {
    val spaceBetweenXes = (size.width.toDp() - spacingX) / xAxisSize
    val strokePathOfQuadraticLine = drawLineAsQuadratic(
        line = line,
        lowerValue = lowerValue,
        upperValue = upperValue,
        spaceBetweenXes = spaceBetweenXes,
        animatedProgress = animatedProgress,
        xAxisSize = xAxisSize,
        spacingX=spacingX,
        spacingY=spacingY,
    )

    if (line.lineShadow == LineShadow.SHADOW) {
        val fillPath = strokePathOfQuadraticLine.apply {
            lineTo((size.width.toDp() - spaceBetweenXes).toPx(), (size.height.toDp() - spacingY).toPx())
            lineTo(spacingX.toPx(), (size.height.toDp() - spacingY).toPx())
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
    xAxisSize: Int,
    spacingX:Dp,
    spacingY: Dp,
) = Path().apply {
    var medX: Float
    var medY: Float
    val height = size.height.toDp()
    drawPathLineWrapper(
        lineParameter = line,
        strokePath = this,
        xAxisSize = xAxisSize,
        animatedProgress = animatedProgress,
        spacingY = spacingY,
        spacingX = spacingX,
    ) { lineParameter, index, maxX, maxY ->

        val nextInfo = lineParameter.data.getOrNull(index + 1) ?: lineParameter.data.last()
        val firstRatio = (lineParameter.data[index] - lowerValue) / (upperValue - lowerValue)
        val secondRatio = (nextInfo - lowerValue) / (upperValue - lowerValue)

        val xFirstPoint = spacingX.toPx() + index * spaceBetweenXes.toPx()
        val xSecondPoint = spacingX.toPx() + (index + 1) * spaceBetweenXes.toPx()

        val yFirstPoint = height.toPx() - spacingY.toPx() - (firstRatio * height.toPx())
        val ySecondPoint = height.toPx() - spacingY.toPx() - (secondRatio * height.toPx())


        // Adjust the coordinates to stay within boundaries
        val x1Adjusted = xFirstPoint.coerceAtMost(maxX.toPx() - spacingX.toPx()).coerceAtLeast(spacingX.toPx())
        val y1Adjusted = yFirstPoint.coerceAtMost(maxY.toDouble()).coerceAtLeast((2 * spacingY.toPx().toDouble()))
        val x2Adjusted = xSecondPoint.coerceAtMost(maxX.toPx() - spacingX.toPx()).coerceAtLeast(spacingX.toPx())
        val y2Adjusted = ySecondPoint.coerceAtMost(maxY.toDouble()).coerceAtLeast((2 * spacingY.toPx().toDouble()))

        if (index == 0) {
            moveTo(x1Adjusted, y1Adjusted.toFloat())
        } else {
            medX = ((x1Adjusted + x2Adjusted) / 2f).toDp().toPx()
            medY = ((y1Adjusted + y2Adjusted) / 2f).toFloat()
            quadraticBezierTo(x1Adjusted, y1Adjusted.toFloat(), medX, medY)
        }
    }

}