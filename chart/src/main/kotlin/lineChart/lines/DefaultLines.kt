package lineChart.lines

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import drawPathLineWrapper
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

    val strokePathOfDefaultLine = drawLineAsDefault(
        lineParameter = line,
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


private fun DrawScope.drawLineAsDefault(
    lineParameter: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    spacing: Dp,
    spaceBetweenXes: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int
) = Path().apply {

    val height = size.height.toDp()

    drawPathLineWrapper(
        lineParameter = lineParameter,
        spacing = spacing,
        strokePath = this,
        xAxisSize = xAxisSize
    ) { lineParameter, index, maxX, maxY ->

        val info = lineParameter.data[index]
        val ratio = (info.toFloat() - lowerValue) / (upperValue - lowerValue)

        val startXPoint = spacing.toPx() + index * spaceBetweenXes.toPx()
        val startyPoint = height - spacing - (ratio * height.toPx() * animatedProgress.value).toDp()


        // Adjust the coordinates to stay within boundaries
        val xAdjusted = startXPoint.coerceIn(spacing.toPx(), maxX - spacing.toPx())
        val yAdjusted = startyPoint.coerceIn(spacing, maxY.toDp())


        if (index == 0) {
            moveTo(xAdjusted, yAdjusted.toPx())
        } else {
            lineTo(xAdjusted, yAdjusted.toPx())
        }
    }

}