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
    xRegionWidthWithoutSpacing: Float,
    barSize: Int,
) {
    if (specialChart) {
        return
    }

    println("xRegionWidth -------- ${barWidth.dp.toPx()}")
    println("barSize -------- ${(barSize.dp.toPx() / 2.5.dp.toPx())}")

    xAxisData.forEachIndexed { index, dataPoint ->

//        val xLength = (barWidth / xAxisData.size) * xRegionWidthWithoutSpacing * index
        val xLength =
            ((barWidth.dp.toPx() * barSize.dp.toPx()) / (barSize.dp.toPx() / 1.2f)) + (((barWidth.dp.toPx() * barSize.dp.toPx()) / 0.7.dp.toPx()) * index)


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