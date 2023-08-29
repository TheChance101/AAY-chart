package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import com.aay.compose.utils.formatToThousandsMillionsBillions


@OptIn(ExperimentalTextApi::class)
fun DrawScope.yAxisDrawing(
    upperValue: Float, lowerValue: Float,
    textMeasure: TextMeasurer,
    spacing: Dp,
    yAxisStyle: TextStyle,
    yAxisRange: Int,
    specialChart: Boolean,
    isFromBarChart: Boolean,
) {
    if (specialChart) {
        return
    }
    val dataRange = upperValue - lowerValue
    val dataStep = dataRange / yAxisRange
    val range = if (isFromBarChart) yAxisRange + 1 else yAxisRange

    (0..range).forEach { i ->
        val yValue = if (isFromBarChart) {
            dataStep * i
        } else {
            lowerValue + dataStep * i
        }

        val y = (size.height.toDp() - spacing - i * (size.height.toDp() - spacing) / range)
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = yValue.formatToThousandsMillionsBillions(),
                style = yAxisStyle,
                topLeft = Offset(0f, y.toPx())
            )
        }
    }
}
