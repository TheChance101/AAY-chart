package lineChart.components

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
import lineChart.model.LineType

fun <T> DrawScope.linesDrawing(
    lineParametersList: List<LineParameters>,
    xAxisData: List<T>,
    ratioLowerValue: Float,
    ratioUpperValue: Float,
    spacing: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>
) {

    val spaceBetweenXes = (size.width - spacing.toPx()) / xAxisData.size

    val maxX = size.width + xAxisData.size
    val maxY = size.height - spacing.toPx()

    // Lines drawing
    lineParametersList.forEach { line ->
        if (line.lineType == LineType.DEFAULT_LINE) {
            val strokePathDefault = Path().apply {
                val height = size.height
                line.data.indices.forEach { i ->
                    val info = line.data[i]
                    val ratio = (info.toFloat() - ratioLowerValue) / (ratioUpperValue - ratioLowerValue)

                    val x1 = spacing.toPx() + i * spaceBetweenXes
                    val y1 = height - spacing.toPx() - (ratio * height * animatedProgress.value)

                    // Adjust the coordinates to stay within boundaries
                    val xAdjusted = x1.coerceIn(spacing.toPx(), maxX - spacing.toPx())
                    val yAdjusted = y1.coerceIn(spacing.toPx(), maxY)

                    if (i == 0) {
                        moveTo(xAdjusted, yAdjusted)
                    } else {
                        lineTo(xAdjusted, yAdjusted)
                    }
                }
            }

            drawPath(
                path = strokePathDefault, color = line.lineColor, style = Stroke(
                    width = 3.dp.toPx(), cap = StrokeCap.Round
                )
            )

            if (line.lineShadow == LineShadow.SHADOW) {
                val fillPath = strokePathDefault.apply {
                    lineTo(size.width - spaceBetweenXes, size.height - spacing.toPx())
                    lineTo(spacing.toPx(), size.height - spacing.toPx())
                    close()
                }

                drawPath(
                    path = fillPath, brush = Brush.verticalGradient(
                        colors = listOf(
                            line.lineColor.copy(alpha = .3f), Color.Transparent
                        ), endY = size.height - spacing.toPx()
                    )
                )
            }
        } else {
            var medX: Float
            var medY: Float
            val strokePath = Path().apply {
                val height = size.height
                line.data.indices.forEach { i ->
                    val nextInfo = line.data.getOrNull(i + 1) ?: line.data.last()
                    val firstRatio = (line.data[i].toFloat() - ratioLowerValue) / (ratioUpperValue - ratioLowerValue)
                    val secondRatio = (nextInfo.toFloat() - ratioLowerValue) / (ratioUpperValue - ratioLowerValue)

                    val x1 = spacing.toPx() + i * spaceBetweenXes
                    val y1 = height - spacing.toPx() - (firstRatio * height * animatedProgress.value)
                    val x2 = spacing.toPx() + (i + 1) * spaceBetweenXes
                    val y2 = height - spacing.toPx() - (secondRatio * height * animatedProgress.value)

                    // Adjust the coordinates to stay within boundaries
                    val x1Adjusted = x1.coerceIn(spacing.toPx(), maxX - spacing.toPx())
                    val y1Adjusted = y1.coerceIn(spacing.toPx(), maxY)
                    val x2Adjusted = x2.coerceIn(spacing.toPx(), maxX - spacing.toPx())
                    val y2Adjusted = y2.coerceIn(spacing.toPx(), maxY)

                    if (i == 0) {
                        moveTo(x1Adjusted, y1Adjusted)
                    } else {
                        medX = (x1Adjusted + x2Adjusted) / 2f
                        medY = (y1Adjusted + y2Adjusted) / 2f
                        quadraticBezierTo(x1Adjusted, y1Adjusted, medX, medY)
                    }
                }
            }
            drawPath(
                path = strokePath, color = line.lineColor, style = Stroke(
                    width = 3.dp.toPx(), cap = StrokeCap.Round
                )
            )

            if (line.lineShadow == LineShadow.SHADOW) {
                val fillPath = strokePath.apply {
                    lineTo(size.width - spaceBetweenXes, size.height - spacing.toPx())
                    lineTo(spacing.toPx(), size.height - spacing.toPx())
                    close()
                }

                drawPath(
                    path = fillPath, brush = Brush.verticalGradient(
                        colors = listOf(
                            line.lineColor.copy(alpha = .3f), Color.Transparent
                        ), endY = size.height - spacing.toPx()
                    )
                )
            }
        }
    }
}