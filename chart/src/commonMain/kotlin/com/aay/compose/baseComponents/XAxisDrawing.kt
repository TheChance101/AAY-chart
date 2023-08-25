package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.xAxisDrawing(
    xAxisData: List<T>,
    spacing: Dp,
    textMeasure: TextMeasurer,
    xAxisStyle: TextStyle,
    specialChart: Boolean
) {
    if (specialChart) {
        return
    }
    val spaceBetweenXes = 100.dp.toPx()

    xAxisData.forEachIndexed { index, dataPoint ->
        val xLength = (spaceBetweenXes - 30.dp.toPx()) + (index * spaceBetweenXes)
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = dataPoint.toString(),
                style = xAxisStyle,
                maxLines = 1,
                topLeft = Offset(
                    xLength.coerceAtMost(size.width),
                    size.height / 1.07f
                )
            )
        }
    }
}
@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.xAxisDrawing(
    xAxisData: List<T>,
    spacing: Dp,
    textMeasure: TextMeasurer,
    xAxisStyle: TextStyle,
    specialChart: Boolean,
    xRegionWidth:Float,
    xRegionWidthWithoutSpacing: Float,
) {
    if (specialChart) {
        return
    }
    val spaceBetweenXes = 100.dp.toPx()

    xAxisData.forEachIndexed { index, dataPoint ->
        val xLength = spaceBetweenXes + (index * xRegionWidth)
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = dataPoint.toString(),
                style = xAxisStyle,
                maxLines = 1,
                topLeft = Offset(
                    xLength.coerceAtMost(size.width),
                    size.height / 1.07f
                )
            )
        }
    }
}