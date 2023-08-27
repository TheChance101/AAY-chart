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
    specialChart: Boolean,
) {
    if (specialChart) {
        return
    }

    xAxisData.forEachIndexed { index, dataPoint ->
        val textLayoutResult = textMeasure.measure(
            text = AnnotatedString(xAxisData[index].toString()),
        ).size.width

        val startSpace = (spacing) + (textLayoutResult).dp
        val spaceBetweenXes = (size.width - startSpace.toPx()) / (xAxisData.size - 1)

        val xLength = (spacing) + (index * spaceBetweenXes).toDp()
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = dataPoint.toString(),
                style = xAxisStyle,
                maxLines = 1,
                topLeft = Offset(
                    xLength.coerceAtMost(size.width.toDp()).toPx(),
                    size.height / 1.07f
                )
            )
        }
    }
}