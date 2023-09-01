package com.aay.compose.donutChart.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

internal fun DrawScope.draPieCircle(
    circleColor: Color,
    radiusRatioCircle: Float
) {
    drawCircle(
        color = circleColor,
        radius = radiusRatioCircle,
        style = Stroke(1.dp.toPx(), cap = StrokeCap.Round)
    )
}