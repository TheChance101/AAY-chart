package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@OptIn(ExperimentalTextApi::class)
fun DrawScope.yAxisDrawing(
    upperValue: Float, lowerValue: Float,
    textMeasure: TextMeasurer,
    spacing: Dp,
    yAxisStyle: TextStyle,
    yAxisRange : Int,
    chartHeight : Dp
) {
    val dataRange = upperValue - lowerValue
    val dataStep = dataRange / yAxisRange

    (0..yAxisRange).forEach { i ->
        val yValue = 0 + dataStep * i
        val y = (size.height.toDp() - spacing - i * (size.height.toDp() - spacing) / (yAxisRange))
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = yValue.toLong().formatToThousandsMillionsBillions(),
                style = yAxisStyle,
                topLeft = Offset(0f, y.toPx())
            )
        }
    }
}

private fun Long.formatToThousandsMillionsBillions(): String {
    return when {
        this < 1000 -> "$this"
        this < 1000000 -> "${String.format("%.1f", this.toFloat() / 1000)}k"
        this < 1000000000 -> "${String.format("%.1f", this.toFloat() / 1000000)}M"
        this < 1000000000000 -> "${String.format("%.1f", this.toFloat() / 1000000000)}B"
        else -> "${String.format("%.1f", this.toFloat() / 1000000000000)}T"
    }
}
