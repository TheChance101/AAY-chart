package com.aay.compose.barChart


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.baseChartContainer
import com.aay.compose.barChart.model.BarParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun BarChartContent(
    modifier: Modifier,
    linesParameters: List<BarParameters>,
    gridColor: Color,
    xAxisData: List<String>,
    isShowGrid: Boolean,
    barWidthPx: Dp,
    animateChart: Boolean,
    showGridWithSpacer: Boolean,
    yAxisStyle: TextStyle,
    xAxisStyle: TextStyle,
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

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {

        val spacingX = (size.width / 18.dp.toPx()).dp
        val spacingY = (size.height / 8.dp.toPx()).dp

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
            xAxisStyle = xAxisStyle
        )

        //todo: draw bars here

        linesParameters.forEach { bar ->
            bar.data.forEach { index ->
                val length = (spacingX + 30.dp / 2) + (index.dp)

                drawRect(
                    brush = Brush.verticalGradient(listOf(bar.lineColor, Color.Red)),
                    topLeft = Offset(
                        length.coerceAtMost(size.width.toDp()).toPx(),
                        size.height - (size.height / 1.07f)
                    ),
                    size = Size(
                        width = 50f,
                        height = (index.toFloat() / upperValue).toFloat() * (size.height - spacingY.toPx())
                    )
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

