package com.aay.compose.lineChart.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
fun DrawScope.yAxisDrawing(
    upperValue: Float, lowerValue: Float,
    textMeasure: TextMeasurer, spacing: Dp,
) {
    val dataRange = upperValue - lowerValue
    val dataStep = dataRange / 5f
    val maxY = size.height - spacing.toPx()

    (0..5).forEach { i ->
        val yValue = lowerValue + dataStep * i
        val y = (size.height - spacing.toPx() - i * size.height / 8f).coerceAtMost(maxY)

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = yValue.toLong().formatToThousandsMillionsBillions(),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                ),
                topLeft = Offset(0f, y)
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


