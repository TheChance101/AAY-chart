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
                textMeasurer = textMeasure, text = yValue.toInt().toString(), style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                ), topLeft = Offset((spacing/2).toPx(), y.toPx())
            )
        }
    }
}

