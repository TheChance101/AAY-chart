package com.aay.compose.radarChart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.*
import com.aay.compose.radarChart.model.RadarChartConfig

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawRadarLabels(
    textMeasurer: TextMeasurer,
    radarChartConfig: RadarChartConfig,
    radarLabels: List<String>,
    labelsStyle: TextStyle,
) {
    val labelsEndPoints = radarChartConfig.labelsPoints
    val labelHeight = textMeasurer.measure(AnnotatedString("M")).size.height
    for (line in labelsEndPoints.indices) {
        drawText(
            textMeasurer = textMeasurer,
            text = radarLabels[line],
            style = labelsStyle,
            topLeft = Offset(
                labelsEndPoints[line].x - textMeasurer.measure(
                    AnnotatedString(
                        text = radarLabels[line],
                    ), style = labelsStyle
                ).size.width / 2,
                labelsEndPoints[line].y - labelHeight / 2
            )
        )
    }
}