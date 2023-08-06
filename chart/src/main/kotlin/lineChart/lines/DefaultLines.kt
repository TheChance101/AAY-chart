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


fun DrawScope.drawDefaultLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    spacing: Dp,
    spaceBetweenXes: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int

) {

    val strokePathOfDefaultLine = drawLineAsQuadratic(
        line = line,
        lowerValue = lowerValue,
        upperValue = upperValue,
        spacing = spacing,
        spaceBetweenXes = spaceBetweenXes,
        animatedProgress = animatedProgress,
        xAxisSize = xAxisSize
    )

    if (line.lineShadow == LineShadow.SHADOW) {
        val fillPath = strokePathOfDefaultLine.apply {
            lineTo((size.width.toDp() - spaceBetweenXes).toPx(), size.height - spacing.toPx())
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
    xAxisSize: Int
) = Path().apply {

    val maxY = size.height.toDp().toPx() - spacing.toPx()
    val maxX = size.width + xAxisSize
    val height = size.height.toDp()


    line.data.indices.forEach { i ->
        val info = line.data[i]
        val ratio = (info.toFloat() - lowerValue) / (upperValue - lowerValue)

        val x1 = spacing.toPx() + i * spaceBetweenXes.toPx()
        val y1 = height - spacing - (ratio * height.toPx() * animatedProgress.value).toDp()


        // Adjust the coordinates to stay within boundaries
        val xAdjusted = x1.coerceIn(spacing.toPx(), maxX - spacing.toPx())
        val yAdjusted = y1.coerceIn(spacing, maxY.toDp())


        if (i == 0) {
            moveTo(xAdjusted, yAdjusted.toPx())
        } else {
            lineTo(xAdjusted, yAdjusted.toPx())
        }
    }

    drawPath(
        path = this, color = line.lineColor, style = Stroke(
            width = 3.dp.toPx(), cap = StrokeCap.Round
        )
    )

}