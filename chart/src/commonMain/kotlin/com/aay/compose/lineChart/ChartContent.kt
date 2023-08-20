package com.aay.compose.lineChart


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.aay.compose.baseComponents.baseChartContainer
import com.aay.compose.lineChart.lines.drawDefaultLineWithShadow
import com.aay.compose.lineChart.lines.drawQuarticLineWithShadow
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.aay.compose.utils.checkIfDataValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun ChartContent(
    modifier: Modifier,
    linesParameters: List<LineParameters>,
    gridColor: Color,
    xAxisData: List<String>,
    isShowGrid: Boolean,
    barWidthPx: Dp,
    animateChart: Boolean,
    showGridWithSpacer: Boolean,
    yAxisStyle: TextStyle,
    xAxisStyle: TextStyle,
    yAxisRange: Int
) {

    val textMeasure = rememberTextMeasurer()

    val animatedProgress = remember {
        if (animateChart) Animatable(0f) else Animatable(1f)
    }
    var upperValue by rememberSaveable {
        mutableStateOf(linesParameters.getUpperValue())
    }
    var lowerValue by rememberSaveable {
        mutableStateOf(linesParameters.getLowerValue())
    }
    checkIfDataValid(xAxisData, linesParameters)

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {

        val spacingX = (size.width / 18.dp.toPx()).dp
        val spacingY = (size.height / 8.dp.toPx()).dp
        val chartHeight = size.height.dp - spacingY
        val chartWidth = size.width.dp - spacingX

        baseChartContainer(
            xAxisData = xAxisData,
            textMeasure = textMeasure,
            upperValue = upperValue.toFloat(),
            lowerValue = lowerValue.toFloat(),
            isShowGrid = isShowGrid,
            backgroundLineWidth = barWidthPx.toPx(),
            gridColor = gridColor,
            showGridWithSpacer = showGridWithSpacer,
            spacingX = spacingX,
            spacingY = spacingY,
            yAxisStyle = yAxisStyle,
            xAxisStyle = xAxisStyle,
            yAxisRange = yAxisRange,
            chartHeight = chartHeight
        )

        linesParameters.forEach { line ->
            if (line.lineType == LineType.DEFAULT_LINE) {

                drawDefaultLineWithShadow(
                    line = line,
                    lowerValue = lowerValue.toFloat(),
                    upperValue = upperValue.toFloat(),
                    animatedProgress = animatedProgress,
                    xAxisSize = xAxisData.size,
                    spacingX = spacingX,
                    spacingY = spacingY,
                )

            } else {
                drawQuarticLineWithShadow(
                    line = line,
                    lowerValue = lowerValue.toFloat(),
                    upperValue = upperValue.toFloat(),
                    animatedProgress = animatedProgress,
                    xAxisSize = xAxisData.size,
                    spacingX = spacingX,
                    spacingY = spacingY,
                )

            }
        }
    }

    LaunchedEffect(linesParameters, animateChart) {
        if (animateChart) {

            collectToSnapShotFlow(linesParameters) {
                upperValue = it.getUpperValue()
                lowerValue = it.getLowerValue()
            }

            delay(400)
            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }
}

private fun List<LineParameters>.getUpperValue(): Double {
    return this.flatMap { item -> item.data }.maxOrNull()?.plus(1.0) ?: 0.0
}

private fun List<LineParameters>.getLowerValue(): Double {
    return this.flatMap { item -> item.data }.minOrNull() ?: 0.0
}

private fun CoroutineScope.collectToSnapShotFlow(
    linesParameters: List<LineParameters>,
    makeUpdateData: (List<LineParameters>) -> Unit
) {
    this.launch {
        snapshotFlow {
            linesParameters
        }.collect {
            makeUpdateData(it)
        }
    }
}

