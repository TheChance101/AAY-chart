package com.aay.compose.lineChart.lines

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.aay.compose.lineChart.model.LineParameters


fun DrawScope.drawDefaultLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    xAxisSize: Int,
    spacingX: Dp,
    spacingY: Dp,
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
    )

    if (line.lineShadow) {
        val fillPath = strokePathOfDefaultLine.apply {
            lineTo(size.width.toDp().toPx(), size.height - spacingY.toPx())
            lineTo(spacingX.toPx(), (size.height.toDp() - spacingY).toPx())
            close()
        }
        clipRect(right = size.width * animatedProgress.value) {
            drawPath(
                path = fillPath, brush = Brush.verticalGradient(
                    colors = listOf(
                        line.lineColor.copy(alpha = .3f), Color.Transparent
                    ), endY = (size.height.toDp() - spacingY).toPx() // for shadow height inside line
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
) = Path().apply {

    val height = size.height.toDp()
    drawPathLineWrapper(
        lineParameter = lineParameter,
        strokePath = this,
        animatedProgress = animatedProgress,
    ) { lineParameter, index ->

        val info = lineParameter.data[index]
        val ratio = (info - lowerValue) / (upperValue - lowerValue)
        val startXPoint = (spacingX + 50.dp / 2) + (index * spaceBetweenXes)
        val startYPoint =
            (height.toPx() + 5.dp.toPx() - spacingY.toPx() - (ratio * (height.toPx() - spacingY.toPx())))

        if (index == 0) {
            moveTo(startXPoint.toPx(), startYPoint.toFloat())
        } else {
            lineTo(startXPoint.toPx(), startYPoint.toFloat())
        }
    }

}