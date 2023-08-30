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
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import com.aay.compose.pieChart.model.PieChartData
import com.aay.compose.pieChart.model.ChartTypes
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawPedigreeChart(
    pieValueWithRatio: List<Float>,
    pieChartData: List<PieChartData>,
    totalSum: Float,
    transitionProgress: Animatable<Float, AnimationVector1D>,
    textMeasure: TextMeasurer,
    textRatioStyle: TextStyle,
    ratioLineColor: Color,
    arcWidth: Float,
    minValue: Float,
    pieChart: ChartTypes
) {
    val outerCircularRadius = (minValue / 2) + (arcWidth / 1.2f)
    var startArc = -90F
    var startArcWithoutAnimation = -90f
    pieValueWithRatio.forEachIndexed { index, _ ->

        val arcWithAnimation = calculateAngle(
            dataLength = pieChartData[index].data.toFloat(),
            totalLength = totalSum,
            progress = transitionProgress.value
        )
        val arcWithoutAnimation = calculateAngle(
            dataLength = pieChartData[index].data.toFloat(), totalLength = totalSum
        )
        val angleInRadians = (startArcWithoutAnimation + arcWithoutAnimation / 2).degreeToAngle
        if (pieChart == ChartTypes.PIE_CHART) {
            val lineStart = Offset(
                center.x + (outerCircularRadius * 1.18f) * cos(angleInRadians) * 0.8f,
                center.y + (outerCircularRadius * 1.18f) * sin(angleInRadians) * 0.8f
            )
            val lineEnd = Offset(
                center.x + (outerCircularRadius * 1.18f) * cos(angleInRadians) * 1.1f,
                center.y + (outerCircularRadius * 1.18f) * sin(angleInRadians) * 1.1f
            )
            val arcOffset = Offset(center.x - (minValue / 2), center.y - (minValue / 2))
            val region = pieValueWithRatio.subList(0, index).sum()
            val regionSign = if (region >= 180f) {
                1
            } else {
                -1
            }
            val secondLineEnd = Offset(lineEnd.x + (arcWidth * regionSign), lineEnd.y)
            drawLines(ratioLineColor, lineStart, lineEnd, secondLineEnd)
            scale(1.3f){
            drawArc(
                color = pieChartData[index].color,
                startAngle = startArc,
                sweepAngle = arcWithAnimation,
                useCenter = true,
                size = Size(minValue, minValue),
                topLeft = arcOffset
            )}
            val textOffset = getTextOffsetByRegion(regionSign, lineEnd.x, secondLineEnd.y, arcWidth)
            ratioText(
                textMeasure,
                getPartRatio(pieValueWithRatio, index),
                textRatioStyle, Offset(textOffset.x, textOffset.y - 20.toDp().toPx())
            )
            startArc += arcWithAnimation
            startArcWithoutAnimation += arcWithoutAnimation

        } else {
            val lineStart = Offset(
                center.x + (outerCircularRadius * 1.18f) * cos(angleInRadians) * 0.8f,
                center.y + (outerCircularRadius * 1.18f) * sin(angleInRadians) * 0.8f
            )
            val lineEnd = Offset(
                center.x + (outerCircularRadius * 1.18f) * cos(angleInRadians) * 1.1f,
                center.y + (outerCircularRadius * 1.18f) * sin(angleInRadians) * 1.1f
            )
            val arcOffset = Offset(center.x - (minValue / 2), center.y - (minValue / 2))
            val region = pieValueWithRatio.subList(0, index).sum()
            val regionSign = if (region >= 180f) {
                1
            } else {
                -1
            }
            val secondLineEnd = Offset(lineEnd.x + (arcWidth * regionSign), lineEnd.y)
            drawLines(ratioLineColor, lineStart, lineEnd, secondLineEnd)
            drawArc(
                color = pieChartData[index].color,
                startAngle = startArc,
                sweepAngle = arcWithAnimation,
                useCenter = false,
                style = Stroke(
                    arcWidth, cap = StrokeCap.Butt
                ),
                size = Size(minValue, minValue),
                topLeft = arcOffset
            )
            val textOffset = getTextOffsetByRegion(regionSign, lineEnd.x, secondLineEnd.y, arcWidth)
            ratioText(
                textMeasure,
                getPartRatio(pieValueWithRatio, index),
                textRatioStyle, Offset(textOffset.x, textOffset.y - 20.toDp().toPx())
            )
            startArc += arcWithAnimation
            startArcWithoutAnimation += arcWithoutAnimation
        }
    }
}

private val Float.degreeToAngle
    get() = (this * Math.PI / 180f).toFloat()

private fun calculateAngle(dataLength: Float, totalLength: Float, progress: Float): Float =
    -360F * dataLength * progress / totalLength

private fun calculateAngle(dataLength: Float, totalLength: Float): Float =
    -360F * dataLength / totalLength

private fun getPartRatio(pieValueWithRatio: List<Float>, index: Int): Int {
    return (pieValueWithRatio[index].toDouble() / 360.0 * 100).roundToInt()
}

private fun getTextOffsetByRegion(regionSign: Int, x: Float, y: Float, arcWidth: Float): Offset {
    return if (regionSign == 1) {
        Offset(x + arcWidth / 4, y)
    } else {
        Offset(x - arcWidth, y)
    }
}




