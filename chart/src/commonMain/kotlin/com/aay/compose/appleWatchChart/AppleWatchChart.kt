package com.aay.compose.appleWatchChart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.DefaultTintBlendMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.appleWatchChart.model.AppleChartParameters


@Composable
fun AppleWatchChart(
    appleChartParameters:List<AppleChartParameters>,
    size: Dp = 280.dp,
    thickness: Dp = 16.dp,
    gapBetweenCircles: Dp = 42.dp
) {
    val data : List<Float> = appleChartParameters.map { it.data }
    val sweepAngles = data.map {
        360 * it / 100
    }
    Canvas(
        modifier = Modifier
            .size(size)
    ) {
        var arcRadius = size.toPx()

        for (i in appleChartParameters.indices) {

            arcRadius -= gapBetweenCircles.toPx()

            drawCircle(
                color = appleChartParameters[i].color.copy(alpha = 0.6f),
                radius = arcRadius / 2,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt),
                blendMode = DefaultTintBlendMode,
                center = Offset(
                    x = (size.toPx() ) / 2,
                    y = (size.toPx() ) / 2
                )
            )

            drawArc(
                color = appleChartParameters[i].color,
                startAngle = -90f,
                sweepAngle = sweepAngles[i],
                useCenter = false,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round),
                size = Size(arcRadius, arcRadius),
                topLeft = Offset(
                    x = (size.toPx() - arcRadius) / 2,
                    y = (size.toPx() - arcRadius) / 2
                )
            )
        }
    }
}
