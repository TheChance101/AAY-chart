package lineChart.lines

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import drawPathLineWrapper
import lineChart.model.LineParameters
import lineChart.model.LineShadow

fun DrawScope.drawQuarticLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int
) {
    val spacingX = (size.width/5f).dp
    val spacingY = (size.height/5f).dp
    val spaceBetweenXes = (size.width.toDp() - spacingX) / xAxisSize
    val strokePathOfQuadraticLine = drawLineAsQuadratic(
        line = line,
        lowerValue = lowerValue,
        upperValue = upperValue,
        spaceBetweenXes = spaceBetweenXes,
        animatedProgress = animatedProgress,
        xAxisSize = xAxisSize
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
    xAxisSize: Int
) = Path().apply {
    val spacingX = (size.width/5f).dp
    val spacingY = (size.height/5f).dp
    var medX: Float
    var medY: Float
    val height = size.height.toDp()
    drawPathLineWrapper(
        lineParameter = line,
        strokePath = this,
        xAxisSize = xAxisSize,
        animatedProgress = animatedProgress
    ) { lineParameter, index, maxX, maxY ->

        val nextInfo = lineParameter.data.getOrNull(index + 1) ?: lineParameter.data.last()
        val firstRatio = (lineParameter.data[index].toFloat() - lowerValue) / (upperValue - lowerValue)
        val secondRatio = (nextInfo.toFloat() - lowerValue) / (upperValue - lowerValue)

        val xFirstPoint = spacingX.toPx() + index * spaceBetweenXes.toPx()
        val xSecondPoint = spacingX.toPx() + (index + 1) * spaceBetweenXes.toPx()

        val yFirstPoint = height - spacingY - (firstRatio * height.toPx()).toDp()
        val ySecondPoint = height - spacingY - (secondRatio * height.toPx()).toDp()


        // Adjust the coordinates to stay within boundaries
        val x1Adjusted = xFirstPoint.coerceAtMost(maxX - spacingX.toPx()).coerceAtLeast(spacingX.toPx())
        val y1Adjusted = yFirstPoint.coerceAtMost(maxY.toDp()).coerceAtLeast((2 * spacingY.toPx()).toDp())
        val x2Adjusted = xSecondPoint.coerceAtMost(maxX - spacingX.toPx()).coerceAtLeast(spacingX.toPx())
        val y2Adjusted = ySecondPoint.coerceAtMost(maxY.toDp()).coerceAtLeast((2 * spacingY.toPx()).toDp())

        if (index == 0) {
            moveTo(x1Adjusted, y1Adjusted.toPx())
        } else {
            medX = (x1Adjusted + x2Adjusted) / 2f
            medY = ((y1Adjusted + y2Adjusted) / 2f).toPx()
            quadraticBezierTo(x1Adjusted, y1Adjusted.toPx(), medX, medY)
        }
    }

}