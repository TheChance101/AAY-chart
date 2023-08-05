package linear


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import linear.chart_components.chartContainer
import linear.model.BackGroundGrid
import linear.model.LineParameters
import linear.model.LineShadow
import linear.model.LineType

@OptIn(ExperimentalTextApi::class)
@Composable
fun LinearChart(
    modifier: Modifier = Modifier,
    linesParameters: List<LineParameters> = ChartDefault.chart.lines,
    backGroundGrid: BackGroundGrid = ChartDefault.chart.backGroundGrid,
    backGroundColor: Color = ChartDefault.chart.backGroundColor,
    xAxisLabel: String = ChartDefault.chart.xAxisLabel,
    yAxisLabel: String = ChartDefault.chart.yAxisLabel,
    xAxisData: List<String> = ChartDefault.chart.xAxisData,
    animateChart: Boolean = true // Add the animateChart property and set a default value
) {
    val spacing = 100f
    val upperValue = remember {
        linesParameters.flatMap { it.data }.maxOrNull()?.plus(1.0) ?: 0.0
    }
    val lowerValue = remember {
        linesParameters.flatMap { it.data }.minOrNull() ?: 0.0
    }

    val yAxis = mutableListOf<Float>()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 16f), 0f)

    val textMeasure = rememberTextMeasurer()

    val animatedProgress = remember { if (animateChart) Animatable(0f) else Animatable(1f) }


    LaunchedEffect(animateChart) {
        if (animateChart) {
            animatedProgress.animateTo(
                targetValue = 1f, animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize().clipToBounds()) {
        val spaceBetweenXes = (size.width - spacing) / xAxisData.size
        val barWidthPx = 0.2.dp.toPx()

        chartContainer(
            xAxisData = xAxisData,
            spacing = spacing.dp,
            textMeasure,
            upperValue.toFloat(),
            lowerValue.toFloat()
        )

        // Calculate the valid boundaries of the chart area
        val minX = spacing
        val maxX = size.width + xAxisData.size
        val minY = spacing
        val maxY = size.height - spacing

        // Draw background lines
        (0..5).forEach { i ->
            yAxis.add(size.height - spacing - i * size.height / 8f)
            val y = yAxis[i] + 15f

            // Ensure the line stays within the boundaries
            val xStart = (spacing - 10).coerceIn(minX, maxX)
            val xEnd = (size.width).coerceIn(minX, maxX - spacing - 45)

            drawLine(
                backGroundColor,
                start = Offset(xStart, y),
                end = Offset(xEnd, y),
                strokeWidth = barWidthPx,
                pathEffect = pathEffect
            )
        }


        // Lines drawing
        linesParameters.forEach { line ->
            if (line.lineType == LineType.DEFAULT_LINE) {
                val strokePathDefault = Path().apply {
                    val height = size.height
                    line.data.indices.forEach { i ->
                        val info = line.data[i]
                        val ratio = (info.toFloat() - lowerValue) / (upperValue - lowerValue)

                        val x1 = spacing + i * spaceBetweenXes
                        val y1 = height - spacing - (ratio * height * animatedProgress.value).toFloat()

                        // Adjust the coordinates to stay within boundaries
                        val xAdjusted = x1.coerceIn(minX, maxX - spacing)
                        val yAdjusted = y1.coerceIn(minY, maxY)

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
                        lineTo(size.width - spaceBetweenXes, size.height - spacing)
                        lineTo(spacing, size.height - spacing)
                        close()
                    }

                    drawPath(
                        path = fillPath, brush = Brush.verticalGradient(
                            colors = listOf(
                                line.lineColor.copy(alpha = .3f), Color.Transparent
                            ), endY = size.height - spacing
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
                        val firstRatio = (line.data[i].toFloat() - lowerValue) / (upperValue - lowerValue)
                        val secondRatio = (nextInfo.toFloat() - lowerValue) / (upperValue - lowerValue)

                        val x1 = spacing + i * spaceBetweenXes
                        val y1 = height - spacing - (firstRatio * height * animatedProgress.value).toFloat()
                        val x2 = spacing + (i + 1) * spaceBetweenXes
                        val y2 = height - spacing - (secondRatio * height * animatedProgress.value).toFloat()

                        // Adjust the coordinates to stay within boundaries
                        val x1Adjusted = x1.coerceIn(minX, maxX - spacing)
                        val y1Adjusted = y1.coerceIn(minY, maxY)
                        val x2Adjusted = x2.coerceIn(minX, maxX - spacing)
                        val y2Adjusted = y2.coerceIn(minY, maxY)

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
                        lineTo(size.width - spaceBetweenXes, size.height - spacing)
                        lineTo(spacing, size.height - spacing)
                        close()
                    }

                    drawPath(
                        path = fillPath, brush = Brush.verticalGradient(
                            colors = listOf(
                                line.lineColor.copy(alpha = .3f), Color.Transparent
                            ), endY = size.height - spacing
                        )
                    )
                }
            }
        }
    }
}