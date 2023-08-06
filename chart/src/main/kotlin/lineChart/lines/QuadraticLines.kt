package lineChart.lines

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lineChart.model.LineParameters
import lineChart.model.LineShadow

fun DrawScope.drawQuarticLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    spacing: Dp,
    spaceBetweenXes: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int
) {

    val maxX = size.width + xAxisSize
    val maxY = size.height.toDp().toPx() - spacing.toPx()

    val strokePathOfQuadratic = drawLineAsQuadratic(
        line = line,
        lowerValue = lowerValue,
        upperValue = upperValue,
        spacing = spacing,
        spaceBetweenXes = spaceBetweenXes,
        animatedProgress = animatedProgress,
        maxX = maxX,
        maxY = maxY
    )

    if (line.lineShadow == LineShadow.SHADOW) {
        val fillPath = strokePathOfQuadratic.apply {
            lineTo((size.width.toDp() - spaceBetweenXes).toPx(), (size.height.toDp() - spacing).toPx())
            lineTo(spacing.toPx(), (size.height.toDp() - spacing).toPx())
            close()
        }

        drawPath(
            path = fillPath, brush = Brush.verticalGradient(
                colors = listOf(
                    line.lineColor.copy(alpha = .3f), Color.Transparent
                ), endY = (size.height.toDp() - spacing).toPx()
            )
        )
    }
}


private fun DrawScope.drawLineAsQuadratic(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    spacing: Dp,
    spaceBetweenXes: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    maxX: Float,
    maxY: Float
) = Path().apply {

    var medX: Float
    var medY: Float


    val height = size.height.toDp()
    line.data.indices.forEach { i ->
        val nextInfo = line.data.getOrNull(i + 1) ?: line.data.last()
        val firstRatio = (line.data[i].toFloat() - lowerValue) / (upperValue - lowerValue)
        val secondRatio = (nextInfo.toFloat() - lowerValue) / (upperValue - lowerValue)

        val x1 = spacing.toPx() + i * spaceBetweenXes.toPx()
        val y1 =
            height - spacing - (firstRatio * height.toPx() * animatedProgress.value).toFloat().toDp()
        val x2 = spacing.toPx() + (i + 1) * spaceBetweenXes.toPx()
        val y2 =
            height - spacing - (secondRatio * height.toPx() * animatedProgress.value).toFloat().toDp()

        // Adjust the coordinates to stay within boundaries
        val x1Adjusted = x1.coerceIn(spacing.toPx(), maxX - spacing.toPx())
        val y1Adjusted = y1.coerceIn(spacing, maxY.toDp())
        val x2Adjusted = x2.coerceIn(spacing.toPx(), maxX - spacing.toPx())
        val y2Adjusted = y2.coerceIn(spacing, maxY.toDp())

        if (i == 0) {
            moveTo(x1Adjusted, y1Adjusted.toPx())
        } else {
            medX = (x1Adjusted + x2Adjusted) / 2f
            medY = ((y1Adjusted + y2Adjusted) / 2f).toPx()
            quadraticBezierTo(x1Adjusted, y1Adjusted.toPx(), medX, medY)
        }
    }

    drawPath(
        path = this, color = line.lineColor, style = Stroke(
            width = 3.dp.toPx(), cap = StrokeCap.Round
        )
    )

}