package com.aay.compose.pieChart.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.aay.compose.pieChart.model.PieChartData

internal fun DrawScope.drawPedigreeChart(
    pieValueWithRatio: List<Float>,
    pieChartData: List<PieChartData>,
    totalSum: Float,
    transitionProgress: Animatable<Float, AnimationVector1D>
) {

    var startArc = -90F
    pieValueWithRatio.forEachIndexed { index, value ->
        val arc = calculateAngle(
            dataLength = pieChartData[index].data,
            totalLength = totalSum,
            progress = transitionProgress.value
        )

        drawArc(
            color = pieChartData[index].color,
            startAngle = startArc,
            sweepAngle = arc,
            useCenter = false,
            style = Stroke(
                size.maxDimension.dp.toPx() / 4.dp.toPx(),
                cap = StrokeCap.Butt
            ),
        )
        startArc += arc
    }
}

private fun calculateAngle(dataLength: Float, totalLength: Float, progress: Float): Float =
    -360F * dataLength * progress / totalLength
