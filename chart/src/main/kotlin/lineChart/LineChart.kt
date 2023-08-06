package lineChart


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
import lineChart.components.chartContainer
import lineChart.model.BackGroundGrid
import lineChart.model.LineParameters
import lineChart.model.LineShadow
import lineChart.model.LineType

@OptIn(ExperimentalTextApi::class)
@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    linesParameters: List<LineParameters> = LineChartDefault.lineParameters,
    backGroundColor: Color = LineChartDefault.backGroundColor,
    xAxisData: List<String> = LineChartDefault.xAxisData,
    showBackgroundGrid: BackGroundGrid = LineChartDefault.backGroundGrid,
    animateChart: Boolean = LineChartDefault.anmiteChart // Add the animateChart property and set a default value
) {
    val spacing = 100f
    val upperValue = remember {
        linesParameters.flatMap { it.data }.maxOrNull()?.plus(1.0) ?: 0.0
    }
    val lowerValue = remember {
        linesParameters.flatMap { it.data }.minOrNull() ?: 0.0
    }

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
        val barWidthPx = 4.dp.toPx()

        chartContainer(
            xAxisData = xAxisData,
            spacing = spacing.dp,
            textMeasure = textMeasure,
            upperValue = upperValue.toFloat(),
            lowerValue = lowerValue.toFloat(),
            isShowBackgroundLines = showBackgroundGrid,
            backgroundLineWidth = barWidthPx,
            backGroundLineColor = backGroundColor

        )

        // Calculate the valid boundaries of the chart area
        val maxX = size.width + xAxisData.size
        val maxY = size.height - spacing

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
                        val xAdjusted = x1.coerceIn(spacing, maxX - spacing)
                        val yAdjusted = y1.coerceIn(spacing, maxY)

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
                        val x1Adjusted = x1.coerceIn(spacing, maxX - spacing)
                        val y1Adjusted = y1.coerceIn(spacing, maxY)
                        val x2Adjusted = x2.coerceIn(spacing, maxX - spacing)
                        val y2Adjusted = y2.coerceIn(spacing, maxY)

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