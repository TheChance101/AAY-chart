package com.aay.compose.lineChart


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.aay.compose.baseComponents.baseChartContainer
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.components.drawDefaultLineWithShadow
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.aay.compose.utils.checkIfDataValid
import com.aay.compose.lineChart.components.drawQuarticLineWithShadow
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
    yAxisRange: Int,
    showXAxis: Boolean,
    showYAxis: Boolean,
    specialChart: Boolean,
    onChartClick: (Float, Float) -> Unit,
    clickedPoints: MutableList<Pair<Float, Float>>,
    gridOrientation: GridOrientation,
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
    checkIfDataValid(xAxisData = xAxisData, linesParameters = linesParameters)

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onChartClick(offset.x, offset.y)
                }
            }
    ) {
        val textLayoutResult = textMeasure.measure(
            text = AnnotatedString(xAxisData.last().toString()),
        ).size.width
        val spacingX = (size.width / 50.dp.toPx()).dp
        val spacingY = (size.height / 8.dp.toPx()).dp
        val xRegionWidth = (size.width.toDp() / (xAxisData.size - 1).toDp()).toDp() - (textLayoutResult.toDp() / 2)

        baseChartContainer(
            xAxisData = xAxisData,
            textMeasure = textMeasure,
            upperValue = upperValue.toFloat(),
            lowerValue = lowerValue.toFloat(),
            isShowGrid = isShowGrid,
            backgroundLineWidth = barWidthPx.toPx(),
            gridColor = gridColor,
            showGridWithSpacer = showGridWithSpacer,
            spacingY = spacingY,
            yAxisStyle = yAxisStyle,
            xAxisStyle = xAxisStyle,
            yAxisRange = yAxisRange,
            showXAxis = showXAxis,
            showYAxis = showYAxis,
            specialChart = specialChart,
            isFromBarChart = false,
            gridOrientation = gridOrientation,
            xRegionWidth = xRegionWidth
        )

        if (specialChart) {
            if (linesParameters.size >= 2) {
                throw Exception("Special case must contain just one line")
            }
            linesParameters.forEach { line ->
                drawQuarticLineWithShadow(
                    line = line,
                    lowerValue = lowerValue.toFloat(),
                    upperValue = upperValue.toFloat(),
                    animatedProgress = animatedProgress,
                    spacingX = spacingX,
                    spacingY = spacingY,
                    specialChart = specialChart,
                    clickedPoints = clickedPoints,
                    xRegionWidth = xRegionWidth,
                    textMeasure
                )

            }
        } else {
            if (linesParameters.size >= 2) {
                clickedPoints.clear()
            }
            linesParameters.forEach { line ->
                if (line.lineType == LineType.DEFAULT_LINE) {

                    drawDefaultLineWithShadow(
                        line = line,
                        lowerValue = lowerValue.toFloat(),
                        upperValue = upperValue.toFloat(),
                        animatedProgress = animatedProgress,
                        spacingX = spacingX,
                        spacingY = spacingY,
                        clickedPoints = clickedPoints,
                        textMeasure = textMeasure,
                        xRegionWidth = xRegionWidth
                    )

                } else {
                    drawQuarticLineWithShadow(
                        line = line,
                        lowerValue = lowerValue.toFloat(),
                        upperValue = upperValue.toFloat(),
                        animatedProgress = animatedProgress,
                        spacingX = spacingX,
                        spacingY = spacingY,
                        specialChart = specialChart,
                        clickedPoints = clickedPoints,
                        xRegionWidth = xRegionWidth,
                        textMeasure
                    )

                }
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
    makeUpdateData: (List<LineParameters>) -> Unit,
) {
    this.launch {
        snapshotFlow {
            linesParameters
        }.collect {
            makeUpdateData(it)
        }
    }
}

