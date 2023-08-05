package linear.chart_components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
fun DrawScope.yAxisDrawing(upperValue : Float, lowerValue : Float,
                                        textMeasure : TextMeasurer
){
    val spacing = 75.dp
    val dataRange = upperValue - lowerValue
    val dataStep = dataRange / 5f
    val minY = spacing
    val maxY = size.height - spacing.toPx()

    (0..5).forEach { i ->
        val yValue = lowerValue + dataStep * i
        val y = (size.height - spacing.toPx() - i * size.height / 8f).coerceIn(minY.toPx(), maxY)

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure, text = yValue.toInt().toString(), style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                ), topLeft = Offset(0f, y)
            )
        }
    }
}

