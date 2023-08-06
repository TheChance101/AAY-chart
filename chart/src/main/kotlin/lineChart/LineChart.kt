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
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lineChart.components.chartContainer
import lineChart.model.BackGroundGrid
import lineChart.model.LineParameters

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
    val upperValue = remember {
        linesParameters.flatMap { it.data }.maxOrNull()?.plus(1.0) ?: 0.0
    }
    val lowerValue = remember {
        linesParameters.flatMap { it.data }.minOrNull() ?: 0.0
    }

    val textMeasure = rememberTextMeasurer()

    val animatedProgress = remember { if (animateChart) Animatable(0f) else Animatable(1f) }


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

    }

    LaunchedEffect(animateChart) {
        if (animateChart) {
            animatedProgress.animateTo(
                targetValue = 1f, animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }
}