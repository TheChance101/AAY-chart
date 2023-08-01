package chart


import ChartDefault
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
import model.BackGroundGrid
import model.LineParameters
import model.LineShadow
import model.LineType

@OptIn(ExperimentalTextApi::class)
@Composable
fun AxesDrawing(
    modifier: Modifier = Modifier,
    linesParameters: List<LineParameters> =ChartDefault.chart.lines,
    backGroundGrid: BackGroundGrid = ChartDefault.chart.backGroundGrid,
    backGroundColor: Color=ChartDefault.chart.backGroundColor,
    xAxisLabel:String=ChartDefault.chart.xAxisLabel,
    yAxisLabel:String=ChartDefault.chart.yAxisLabel,
    ) {
    val spacing = 130f
    val upperValue = remember {
        linesParameters[0].data.maxOfOrNull{ it.second.toDouble() }?.plus(1) ?: 0.0
    }
    val lowerValue = remember {
        linesParameters[0].data.minOfOrNull { it.second.toDouble() } ?: 0.0
    }

    val yAxis = mutableListOf<Float>()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 16f), 0f)

    val textMeasure = rememberTextMeasurer()


    Canvas(modifier = modifier) {
        val spaceBetweenXes = (size.width - spacing) / (linesParameters[0].data.size - 1)
        val barWidthPx = 0.2.dp.toPx()


        linesParameters[0].data.forEachIndexed { index, dataPoint ->
            val xValue = dataPoint.first

            // for x coordinate
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    textMeasurer = textMeasure, text = xAxisLabel,
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
                        textMeasurer = textMeasure, text = yAxisLabel,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Gray,
                        ),
                        topLeft = Offset(0f, size.height - spacing - i * size.height / 8f)
                    )
                }
            }
            linesParameters.forEach { line->
                (0..4).forEach { i ->
                    yAxis.add(size.height - spacing - i * size.height / 8f)
                    drawLine(
                        line.lineColor,
                        start = Offset(spacing - 10, yAxis[i] + 12f),
                        end = Offset(size.width / 1.07f, yAxis[i] + 12f),
                        strokeWidth = barWidthPx,
                        pathEffect = pathEffect
                    )
                }
                if (line.lineType==LineType.DEFAULT_LINE ) {
                    val strokePathDefault = Path().apply {
                        val height = size.height
                        line.data.indices.forEach { i ->
                            val info = line.data[i]
                            val ratio = (info.second.toFloat() - lowerValue) / (upperValue - lowerValue)

                            val x1 = spacing + i * spaceBetweenXes
                            val y1 = height - spacing - (ratio * height).toFloat()

                            if (i == 0) {
                                moveTo(x1, y1)
                            }
                            lineTo(x1, y1)
                        }
                    }

                    drawPath(
                        path = strokePathDefault,
                        color = Color.Blue,
                        style = Stroke(
                            width = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                    if (line.lineShadow==LineShadow.SHADOW) {
                        val fillPath = strokePathDefault.apply {
                            lineTo(size.width - spaceBetweenXes, size.height - spacing)
                            lineTo(spacing, size.height - spacing)
                            close()
                        }

                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Blue.copy(alpha = .3f),
                                    Color.Transparent
                                ),
                                endY = size.height - spacing
                            )
                        )
                    }
                } else {
                    var medX: Float
                    var medY: Float
                    val strokePath = Path().apply {
                        val height = size.height
                        line.data.indices.forEach { i ->
                            val nextInfo = line.data.getOrNull(i + 1) ?: line.data.last()
                            val firstRatio = (line.data[i].second.toFloat() - lowerValue) / (upperValue - lowerValue)
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

                    if (line.lineShadow==LineShadow.SHADOW) {
                        val fillPath = strokePath.apply {
                            lineTo(size.width - spaceBetweenXes, size.height - spacing)
                            lineTo(spacing, size.height - spacing)
                            close()
                        }

                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Red.copy(alpha = .3f),
                                    Color.Transparent
                                ),
                                endY = size.height - spacing
                            )
                        )
                    }
                 }
            }
        }
    }
}