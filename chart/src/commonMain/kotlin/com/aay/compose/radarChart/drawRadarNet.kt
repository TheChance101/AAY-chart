package com.aay.compose.radarChart

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawRadarNet(config: RadarChartConfig) {

    val endPoints = config.endPoints
    val nextStartPoints = config.nextStartPoints
    val nextEndPoints = config.nextEndPoints

    for (endpoint in endPoints) {
        drawLine(
            color = Color.Gray,
            start = center,
            end = endpoint,
            strokeWidth = 2f,
            cap = StrokeCap.Butt
        )
        for (index in nextStartPoints.indices) {
            drawLine(
                color = Color.Gray,
                start = nextStartPoints[index],
                end = nextEndPoints[index],
                strokeWidth = 2f,
                cap = StrokeCap.Butt
            )
        }
    }
}