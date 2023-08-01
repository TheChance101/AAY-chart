package chart


import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.skia.impl.Log

@OptIn(ExperimentalTextApi::class)
@Composable
fun <X, Y : Number> AxesDrawing(
    modifier: Modifier = Modifier,
    data: List<Pair<X, Y>> = emptyList(),
    getXLabel: (X) -> String,
    getYLabel: (Y) -> String,
    drawLineColor: Color
) {
    val spacing = 130f
    val upperValue = remember {
        data.maxOfOrNull { it.second.toDouble() }?.plus(1) ?: 0.0
    }
    val lowerValue = remember {
        data.minOfOrNull { it.second.toDouble() } ?: 0.0
    }

    val yAxis = mutableListOf<Float>()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    val textMeasure = rememberTextMeasurer()


    Canvas(modifier = modifier) {
        val spaceBetweenXes = (size.width - spacing) / (data.size - 1)
        val barWidthPx = 0.2.dp.toPx()


        data.forEachIndexed { index, dataPoint ->
            val xValue = dataPoint.first

            // for x coordinate
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    textMeasurer = textMeasure, text = getXLabel(xValue),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray
                    ),
                    topLeft = Offset(spacing + index * spaceBetweenXes, size.height / 1.07f)
                )
            }


            // for y coordinate
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
                        topLeft = Offset(0f, size.height - spacing - i * size.height / 8f)
                    )
                }

                yAxis.add(size.height - spacing - i * size.height / 8.2f)

                drawLine(
                    drawLineColor,
                    start = Offset(spacing, yAxis[i]),
                    end = Offset(size.width, yAxis[i]),
                    strokeWidth = barWidthPx,
                    pathEffect = pathEffect
                )
            }


            var medX: Float
            var medY: Float
            val strokePath = Path().apply {
                val height = size.height
                data.indices.forEach { i ->
                    val nextInfo = data.getOrNull(i + 1) ?: data.last()
                    val firstRatio = (data[i].second.toFloat() - lowerValue) / (upperValue - lowerValue)
                    val secondRatio = (nextInfo.second.toFloat() - lowerValue) / (upperValue - lowerValue)

                    val x1 = spacing + i * spaceBetweenXes
                    val y1 = height - spacing - (firstRatio * height).toFloat()
                    val x2 = spacing + (i + 1) * spaceBetweenXes
                    val y2 = height - spacing - (secondRatio * height).toFloat()
                    if (i == 0) {
                        moveTo(x1, y1)
                    } else {
                        medX = (x1 + x2) / 2f
                        medY = (y1 + y2) / 2f
                        quadraticBezierTo(x1 = x1, y1 = y1, x2 = medX, y2 = medY)
                    }
                }
            }
            drawPath(
                path = strokePath,
                color = Color.Red,
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )

        }

    }
}