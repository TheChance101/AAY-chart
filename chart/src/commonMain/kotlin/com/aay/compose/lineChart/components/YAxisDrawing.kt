package lineChart.components

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
import androidx.compose.ui.unit.times

@OptIn(ExperimentalTextApi::class)
fun DrawScope.yAxisDrawing(
    upperValue: Float, lowerValue: Float,
    textMeasure: TextMeasurer, spacing: Dp,

    ) {
    val dataRange = upperValue - lowerValue
    val dataStep = dataRange / 6

    (0..6).forEach { i ->
        val yValue = lowerValue + dataStep * i
        val y = (size.height.toDp() - spacing - i * size.height.toDp() / 7)

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = yValue.toLong().formatToThousandsMillionsBillions(),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                ),
                topLeft = Offset(0f, y.toPx())
            )
        }
    }
}

private fun Long.formatToThousandsMillionsBillions(): String {
    return when {
        this < 1000 -> "$this"
        this < 1000000 -> "${(this.toFloat() / 1000).toInt()}k"
        this < 1000000000 -> "${(this.toFloat() / 1000000).toInt()}M"
        this < 1000000000000 -> "${(this.toFloat() / 1000000000).toInt()}B"
        else -> "${(this.toFloat() / 1000000000000).toInt()}T"
    }
}
