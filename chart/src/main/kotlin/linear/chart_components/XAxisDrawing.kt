package linear.chart_components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.xAxisDrawing(xAxisData: List<T>, spacing: Float , textMeasure : TextMeasurer) {
    val spaceBetweenXes = (size.width - spacing) / xAxisData.size
    xAxisData.forEachIndexed { index, dataPoint ->
        val xLength = spacing + index * spaceBetweenXes
        // for x coordinate
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure, text = dataPoint.toString(), style = TextStyle(
                    fontSize = 12.sp, color = Color.Gray
                ), topLeft = Offset(xLength, size.height / 1.07f)
            )
        }
    }
}