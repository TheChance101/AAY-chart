package com.aay.compose.barChart.components


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.baseChartContainer
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.xAxisDrawing
import com.aay.compose.utils.ChartDefaultValues.specialChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun BarChartContent(
    barsParameters: List<BarParameters>,
    gridColor: Color,
    xAxisData: List<String>,
    isShowGrid: Boolean,
    barWidthPx: Dp,
    animateChart: Boolean,
    showGridWithSpacer: Boolean,
    yAxisStyle: TextStyle,
    xAxisStyle: TextStyle,
    backgroundLineWidth: Float,
    yAxisRange: Int,
    showXAxis: Boolean,
    showYAxis: Boolean,
    gridOrientation: Orientation,
    boxSize: Dp,
) {

    val textMeasure = rememberTextMeasurer()

    val animatedProgress = remember(barsParameters) {
        if (animateChart) Animatable(0f) else Animatable(1f)
    }

    var upperValue by rememberSaveable {
        mutableStateOf(barsParameters.getUpperValue())
    }
    var lowerValue by rememberSaveable {
        mutableStateOf(barsParameters.getLowerValue())
    }

    var maxWidth by remember { mutableStateOf(0f) }
    var maxHeight by remember { mutableStateOf(0f) }
    var barWidth by remember { mutableStateOf(0f) }
    var xRegionWidthWithoutSpacing by remember { mutableStateOf(0f) }
    var xRegionWidth by remember { mutableStateOf(0f) }
    var spaceBetweenBars by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val spacingX = (boxSize / 18)
            val spacingY = (boxSize / 8)
            spaceBetweenBars = (boxSize.toPx() / 1000)
            xRegionWidth = (boxSize.toPx() / 5)
            xRegionWidthWithoutSpacing = xRegionWidth - (spacingX.toPx())
            barWidth = (xRegionWidthWithoutSpacing / barsParameters.size) - spaceBetweenBars
            maxWidth = xRegionWidth * xAxisData.size
            maxHeight = size.height - spacingY.toPx()  + 10.dp.toPx()

            baseChartContainer(
                xAxisData = xAxisData,
                textMeasure = textMeasure,
                upperValue = upperValue.toFloat(),
                lowerValue = lowerValue.toFloat(),
                isShowGrid = isShowGrid,
                backgroundLineWidth = backgroundLineWidth,
                gridColor = gridColor,
                showGridWithSpacer = showGridWithSpacer,
                spacingX = spacingX,
                spacingY = spacingY,
                yAxisStyle = yAxisStyle,
                xAxisStyle = xAxisStyle,
                yAxisRange = yAxisRange,
                showXAxis = showXAxis,
                showYAxis = showYAxis,
                gridOrientation = gridOrientation,
                xRegionWidth = xRegionWidth,
                xRegionWidthWithoutSpacing = xRegionWidthWithoutSpacing,
                isFromBarChart = true,
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 55.dp)
                .fillMaxSize().horizontalScroll(rememberScrollState())
        ) {

            Canvas(
                Modifier.width(maxWidth.dp).fillMaxHeight()

            ) {
                val spacingX = (size.width / 18.dp.toPx()).dp

                drawBarGroups(
                    barsParameters = barsParameters,
                    upperValue = upperValue,
                    lowerValue = lowerValue,
                    xAxisData = xAxisData,
                    barWidth = barWidth,
                    xRegionWidth = xRegionWidth,
                    xRegionWidthWithoutSpacing = xRegionWidthWithoutSpacing,
                    spaceBetweenBars = spaceBetweenBars,
                    maxWidth = maxWidth.dp,
                    height = maxHeight.dp
                )

                xAxisDrawing(
                    xAxisData = xAxisData,
                    textMeasure = textMeasure,
                    xAxisStyle = xAxisStyle,
                    specialChart = specialChart,
                    barWidth = barWidth,
                    xRegionWidthWithoutSpacing = xRegionWidthWithoutSpacing,
                    height = maxHeight.dp,
                    barSize = barsParameters.size
                )
            }
        }
    }


    LaunchedEffect(barsParameters, animateChart) {
        if (animateChart) {

            collectToSnapShotFlow(barsParameters) {
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

private fun List<BarParameters>.getUpperValue(): Double {
    return this.flatMap { item -> item.data }.maxOrNull()?.plus(1.0) ?: 0.0
}

private fun List<BarParameters>.getLowerValue(): Double {
    return this.flatMap { item -> item.data }.minOrNull() ?: 0.0
}

private fun CoroutineScope.collectToSnapShotFlow(
    linesParameters: List<BarParameters>,
    makeUpdateData: (List<BarParameters>) -> Unit
) {
    this.launch {
        snapshotFlow {
            linesParameters
        }.collect {
            makeUpdateData(it)
        }
    }
}

