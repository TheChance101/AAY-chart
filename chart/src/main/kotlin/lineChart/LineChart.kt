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
import lineChart.lines.drawDefaultLineWithShadow
import lineChart.lines.drawQuarticLineWithShadow
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
    val spacing = 100.dp


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
            spacing = spacing,
            textMeasure = textMeasure,
            upperValue = upperValue.toFloat(),
            lowerValue = lowerValue.toFloat(),
            isShowBackgroundLines = showBackgroundGrid,
            backgroundLineWidth = barWidthPx.toPx(),
            backGroundLineColor = backGroundColor,
            pathEffect = pathEffect,
        )


        val spaceBetweenXes = (size.width.toDp() - spacing) / xAxisData.size



        linesParameters.forEach { line ->
            if (line.lineType == LineType.DEFAULT_LINE) {

                drawDefaultLineWithShadow(
                    line = line,
                    lowerValue = lowerValue.toFloat(),
                    upperValue = upperValue.toFloat(),
                    spacing = spacing,
                    spaceBetweenXes = spaceBetweenXes,
                    animatedProgress = animatedProgress,
                    xAxisSize = xAxisData.size
                )

            } else {

                drawQuarticLineWithShadow(
                    line = line,
                    lowerValue = lowerValue.toFloat(),
                    upperValue = upperValue.toFloat(),
                    spacing = spacing,
                    spaceBetweenXes = spaceBetweenXes,
                    animatedProgress = animatedProgress,
                    xAxisSize = xAxisData.size
                )
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