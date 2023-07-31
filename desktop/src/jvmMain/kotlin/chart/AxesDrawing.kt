package chart


import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
@Composable
fun <X, Y : Number> AxesDrawing(
    modifier: Modifier = Modifier,
    data: List<Pair<X, Y>> = emptyList(),
    getXLabel: (X) -> String,
    getYLabel: (Y) -> String
) {
    val spacing = 100f
    val upperValue = remember {
        data.maxOfOrNull { it.second.toDouble() }?.plus(1) ?: 0.0
    }
    val lowerValue = remember {
        data.minOfOrNull { it.second.toDouble() } ?: 0.0
    }

    val textMeasure = rememberTextMeasurer()

    Canvas(modifier = modifier) {
        val spaceBetweenXes = (size.width - spacing) / (data.size - 1)
        data.forEachIndexed { i, dataPoint ->
            val xValue = dataPoint.first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    textMeasurer = textMeasure, text = getXLabel(xValue),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray
                    ),
                    topLeft = Offset(spacing + i * spaceBetweenXes , size.height / 1.08f)
                )
            }
            val priceRange = upperValue - lowerValue
            val priceStep = priceRange / 5f
            (0..4).forEach { i ->
                drawContext.canvas.nativeCanvas.apply {
                    val yValue = lowerValue + priceStep * i
                    drawText(
                        textMeasurer = textMeasure, text = getYLabel(yValue as Y),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Gray,
                        ),
                        topLeft = Offset(30f, size.height - spacing - i * size.height / 5f)
                    )
                }
            }
        }

    }
}