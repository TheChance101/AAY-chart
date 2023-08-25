package com.aay.compose.radarChart


import androidx.compose.ui.graphics.drawscope.DrawScope
import com.aay.compose.radarChart.model.NetLinesStyle

fun DrawScope.drawRadarNet(
    netLinesStyle: NetLinesStyle,
    config: RadarChartConfig
) {

    val endPoints = config.endPoints
    val nextStartPoints = config.nextStartPoints
    val nextEndPoints = config.nextEndPoints

    for (endpoint in endPoints) {
        drawLine(
            color = netLinesStyle.netLineColor,
            start = center,
            end = endpoint,
            strokeWidth = netLinesStyle.netLinesStrokeWidth,
            cap = netLinesStyle.netLinesStrokeCap,
        )
        for (index in nextStartPoints.indices) {
            drawLine(
                color = netLinesStyle.netLineColor,
                start = nextStartPoints[index],
                end = nextEndPoints[index],
                strokeWidth = netLinesStyle.netLinesStrokeWidth,
                cap = netLinesStyle.netLinesStrokeCap,
            )
        }
    }
}