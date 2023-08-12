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
import com.aay.compose.lineChart.components.chartContainer
import com.aay.compose.lineChart.model.BackGroundGrid
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.aay.compose.lineChart.lines.drawDefaultLineWithShadow
import com.aay.compose.lineChart.lines.drawQuarticLineWithShadow
import kotlinx.coroutines.launch
import org.jetbrains.skia.impl.Log

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun ChartContent(
    modifier: Modifier,
    linesParameters: List<LineParameters>,
    backGroundColor: Color,
    xAxisData: List<String>,
    showBackgroundGrid: BackGroundGrid,
    barWidthPx: Dp,
    animateChart: Boolean,
    pathEffect: PathEffect,
    yAxisStyle: TextStyle,
    xAxisStyle: TextStyle
) {

    val textMeasure = rememberTextMeasurer()

    val animatedProgress = remember {
        if (animateChart) Animatable(0f) else Animatable(1f)
    }
    var upperValue by rememberSaveable {
        mutableStateOf(linesParameters.flatMap { it.data }.maxOrNull()?.plus(1.0) ?: 0.0)
    }
    var lowerValue by rememberSaveable {
        mutableStateOf(linesParameters.flatMap { it.data }.minOrNull() ?: 0.0)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {

        val spacingX = (size.width / 10.dp.toPx()).dp
        val spacingY = (size.height / 10.dp.toPx()).dp

        chartContainer(
            xAxisData = xAxisData,
            textMeasure = textMeasure,
            upperValue = upperValue.toFloat(),
            lowerValue = lowerValue.toFloat(),
            isShowBackgroundLines = showBackgroundGrid,
            backgroundLineWidth = barWidthPx.toPx(),
            backGroundLineColor = backGroundColor,
            pathEffect = pathEffect,
            spacingX = spacingX,
            spacingY = spacingY,
            yAxisStyle = yAxisStyle,
            xAxisStyle = xAxisStyle
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
            launch {
                snapshotFlow {
                    linesParameters
                }.collect {
                    upperValue = it.flatMap { item -> item.data }.maxOrNull()?.plus(1.0) ?: 0.0
                    lowerValue = it.flatMap { item -> item.data }.minOrNull() ?: 0.0
                }
            }


            delay(400)
            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }
}