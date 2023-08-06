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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
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
    barWidthPx: Dp = LineChartDefault.backgroundLineWidth,
    animateChart: Boolean = LineChartDefault.ANIMATED_CHART,
    pathEffect: PathEffect = LineChartDefault.pathEffect
) {
    val spacing = 100f

    val sspacing2 = 75.dp


    val textMeasure = rememberTextMeasurer()

    val animatedProgress = remember {
        if (animateChart) Animatable(0f) else Animatable(1f)
    }
    val upperValue = remember {
        linesParameters.flatMap { it.data }.maxOrNull()?.plus(1.0) ?: 0.0
    }
    val lowerValue = remember {
        linesParameters.flatMap { it.data }.minOrNull() ?: 0.0
    }


    Canvas(modifier = modifier.fillMaxSize().clipToBounds()) {
        chartContainer(
            xAxisData = xAxisData,
            spacing = spacing.dp,
            textMeasure = textMeasure,
            upperValue = upperValue.toFloat(),
            lowerValue = lowerValue.toFloat(),
            isShowBackgroundLines = showBackgroundGrid,
            backgroundLineWidth = barWidthPx.toPx(),
            backGroundLineColor = backGroundColor,
            pathEffect = pathEffect,
            lineParametersList = linesParameters,
            animatedProgress = animatedProgress
        )

        val spaceBetweenXes = (size.width.toDp() - sspacing2) / xAxisData.size


        val minX = sspacing2
        val maxX = size.width + xAxisData.size
        val minY = sspacing2
        val maxY = size.height.toDp().toPx() - sspacing2.toPx()


        linesParameters.forEach { line ->
            if (line.lineType == LineType.DEFAULT_LINE) {
                val strokePathDefault = Path().apply {
                    val height = size.height.toDp()
                    line.data.indices.forEach { i ->
                        val info = line.data[i]
                        val ratio = (info.toFloat() - lowerValue) / (upperValue - lowerValue)

                        val x1 = sspacing2.toPx() + i * spaceBetweenXes.toPx()
                        val y1 = height - sspacing2 - (ratio * height.toPx() * animatedProgress.value).toFloat().toDp()

                        // Adjust the coordinates to stay within boundaries
                        val xAdjusted = x1.coerceIn(minX.toPx(), maxX - sspacing2.toPx())
                        val yAdjusted = y1.coerceIn(minY, maxY.toDp())

                        if (i == 0) {
                            moveTo(xAdjusted, yAdjusted.toPx())
                        } else {
                            lineTo(xAdjusted, yAdjusted.toPx())
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
                        lineTo((size.width.toDp() - spaceBetweenXes).toPx(), size.height - sspacing2.toPx())
                        lineTo(sspacing2.toPx(), (size.height.toDp() - sspacing2).toPx())
                        close()
                    }

                    drawPath(
                        path = fillPath, brush = Brush.verticalGradient(
                            colors = listOf(
                                line.lineColor.copy(alpha = .3f), Color.Transparent
                            ), endY = (size.height.toDp() - sspacing2).toPx()
                        )
                    )
                }
            } else {
                var medX: Float
                var medY: Float
                val strokePath = Path().apply {
                    val height = size.height.toDp()
                    line.data.indices.forEach { i ->
                        val nextInfo = line.data.getOrNull(i + 1) ?: line.data.last()
                        val firstRatio = (line.data[i].toFloat() - lowerValue) / (upperValue - lowerValue)
                        val secondRatio = (nextInfo.toFloat() - lowerValue) / (upperValue - lowerValue)

                        val x1 = sspacing2.toPx() + i * spaceBetweenXes.toPx()
                        val y1 =
                            height - sspacing2 - (firstRatio * height.toPx() * animatedProgress.value).toFloat().toDp()
                        val x2 = sspacing2.toPx() + (i + 1) * spaceBetweenXes.toPx()
                        val y2 =
                            height - sspacing2 - (secondRatio * height.toPx() * animatedProgress.value).toFloat().toDp()

                        // Adjust the coordinates to stay within boundaries
                        val x1Adjusted = x1.coerceIn(minX.toPx(), maxX - sspacing2.toPx())
                        val y1Adjusted = y1.coerceIn(minY, maxY.toDp())
                        val x2Adjusted = x2.coerceIn(minX.toPx(), maxX - sspacing2.toPx())
                        val y2Adjusted = y2.coerceIn(minY, maxY.toDp())

                        if (i == 0) {
                            moveTo(x1Adjusted, y1Adjusted.toPx())
                        } else {
                            medX = (x1Adjusted + x2Adjusted) / 2f
                            medY = ((y1Adjusted + y2Adjusted) / 2f).toPx()
                            quadraticBezierTo(x1Adjusted, y1Adjusted.toPx(), medX, medY)
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
                        lineTo((size.width.toDp() - spaceBetweenXes).toPx(), (size.height.toDp() - sspacing2).toPx())
                        lineTo(sspacing2.toPx(), (size.height.toDp() - sspacing2).toPx())
                        close()
                    }

                    drawPath(
                        path = fillPath, brush = Brush.verticalGradient(
                            colors = listOf(
                                line.lineColor.copy(alpha = .3f), Color.Transparent
                            ), endY = (size.height.toDp() - sspacing2).toPx()
                        )
                    )
                }
            }


        }

    }


    LaunchedEffect(animateChart) {
        if (animateChart) {
            animatedProgress.animateTo(
                targetValue = 1f, animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }
}