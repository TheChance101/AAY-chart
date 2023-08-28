package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlin.math.pow

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
    val spaceBetweenXes = (size.width - spacing.toPx()) / xAxisData.size

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
    height: Dp,
    textMeasure: TextMeasurer,
    xAxisStyle: TextStyle,
    specialChart: Boolean,
    barWidth: Float,
    xRegionWidth: Float,
    xRegionWidthWithoutSpacing: Float,
    barSize: Int,
    yTextLayoutResult: Int,
) {
    if (specialChart) {
        return
    }

    xAxisData.forEachIndexed { index, dataPoint ->


        val xLength =
              (xRegionWidthWithoutSpacing/4)+ (index * (xRegionWidth.dp ).toPx())

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = dataPoint.toString(),
                style = xAxisStyle,
                maxLines = 1,
                topLeft = Offset(
                    xLength.coerceAtMost(size.width),
                    height.value + 20.dp.toPx()
                )
            )
        }
    }
}