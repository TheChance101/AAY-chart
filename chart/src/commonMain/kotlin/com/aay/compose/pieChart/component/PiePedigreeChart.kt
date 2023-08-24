package com.aay.compose.pieChart.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.pieChart.model.PieChartData
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawPedigreeChart(
    pieValueWithRatio: List<Float>,
    pieChartData: List<PieChartData>,
    totalSum: Float,
    transitionProgress: Animatable<Float, AnimationVector1D>,
    textMeasure: TextMeasurer,
) {
    val minValue = min(size.width, size.height)
        .coerceAtMost(size.height / 2)
        .coerceAtMost(size.width / 2)
    var startArc = -90F
    var startArcWithoutAnimation = -90f
    val arcWidth = (size.minDimension.dp.toPx() * 0.13f).coerceAtMost(minValue / 4)
    val width = size.height
    val radius = width / 2f
    val outerCircularRadius = (minValue / 2) + (arcWidth / 1.2f)

    pieValueWithRatio.forEachIndexed { index, value ->
        val arcWithAnimation = calculateAngle(
            dataLength = pieChartData[index].data,
            totalLength = totalSum,
            progress = transitionProgress.value
        )
        val arcWithoutAnimation = calculateAngle(
            dataLength = pieChartData[index].data, totalLength = totalSum
        )
        val angleInRadians = (startArcWithoutAnimation + arcWithoutAnimation / 2).degreeToAngle
        val lineStart = Offset(
            center.x + (outerCircularRadius * 1.18f) * cos(angleInRadians) * 0.85f,
            center.y + (outerCircularRadius * 1.18f) * sin(angleInRadians) * 0.85f
        )
        val lineEnd = Offset(
            center.x + (outerCircularRadius * 1.18f) * cos(angleInRadians) * 1.1f,
            center.y + (outerCircularRadius * 1.18f) * sin(angleInRadians) * 1.1f
        )
        val region = pieValueWithRatio.subList(0, index).sum()
        var regionSign = 1
        val secondLineEnd =
            if (region >= 180f) {
                regionSign *= 1
                println("min:$region")
                Offset((center.x + (outerCircularRadius + (arcWidth))), lineEnd.y)
            } else {
                regionSign *= -1
                println("max:$region")
                Offset((center.x - (outerCircularRadius + (arcWidth))), lineEnd.y)
            }
        scale(0.8f) {
            draPieCircle(
                circleColor = Color.Gray, radiusRatioCircle = (minValue / 2) + (arcWidth / 1.2f)
            )
            drawLine(
                color = Color.Gray, start = lineStart, end = lineEnd, strokeWidth = 2.dp.toPx()
            )
            drawLine(
                color = Color.Gray,
                start = lineEnd,
                end = Offset(lineEnd.x + (arcWidth * regionSign), lineEnd.y),
                strokeWidth = 2.dp.toPx()
            )
            drawArc(
                color = pieChartData[index].color,
                startAngle = startArc,
                sweepAngle = arcWithAnimation,
                useCenter = false,
                style = Stroke(
                    arcWidth, cap = StrokeCap.Butt
                ),
                size = Size(minValue, minValue),
                topLeft = Offset(center.x - (minValue / 2), center.y - (minValue / 2))
            )
            draPieCircle(
                circleColor = Color.Gray, radiusRatioCircle = (minValue / 2) - (arcWidth / 1.2f)
            )
            val textOffset =getOffsetByRegion(regionSign, lineEnd.x, secondLineEnd.y, arcWidth)
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    textMeasurer = textMeasure,
                    text = "${getPartRatio(pieValueWithRatio, index)}%",
                    style = TextStyle.Default.copy(fontSize = 12.sp),
                    topLeft = Offset(textOffset.x,textOffset.y-20.dp.toPx()) ,
                    overflow = TextOverflow.Visible
                )
            }
        }

        startArc += arcWithAnimation
        startArcWithoutAnimation += arcWithoutAnimation
    }
}

private val Float.degreeToAngle
    get() = (this * Math.PI / 180f).toFloat()

private fun calculateAngle(dataLength: Float, totalLength: Float, progress: Float): Float =
    -360F * dataLength * progress / totalLength

private fun calculateAngle(dataLength: Float, totalLength: Float): Float =
    -360F * dataLength / totalLength

private fun getPartRatio(pieValueWithRatio: List<Float>, index: Int): Int {
    return (pieValueWithRatio[index].toDouble() / 360.0 * 100).toInt()
}

private fun getOffsetByRegion(regionSign: Int, x: Float, y: Float, arcWidth: Float): Offset {
    return if (regionSign == 1) {
        Offset(x + arcWidth / 4, y )
    } else {
        Offset(x - arcWidth , y )
    }
}

