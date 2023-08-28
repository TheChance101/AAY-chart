package com.aay.compose.radarChart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.aay.compose.radarChart.model.RadarChartConfig

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawAxisData(
    labelsStyle: TextStyle,
    scalarValuesStyle: TextStyle,
    textMeasurer: TextMeasurer,
    radarChartConfig: RadarChartConfig,
    radarLabels: List<String>,
    scalarValue: Double,
    scalarSteps: Int,
    unit: String
) {

    val labelsEndPoints = radarChartConfig.labelsPoints
    val nextStartPoints = radarChartConfig.scalarPoints.toMutableList()
    nextStartPoints.add(0, center)
    nextStartPoints.removeAt(nextStartPoints.size - 1)

    val scalarStep = scalarValue / (scalarSteps - 1)
    val textVerticalOffset = 20.toDp().toPx()

    for (step in 0 until scalarSteps) {
        drawText(
            textMeasurer = textMeasurer,
            text = (scalarStep * step).toString() + " " + unit,
            style = scalarValuesStyle,
            topLeft = Offset(
                nextStartPoints[step].x + 5.toDp().toPx(),
                nextStartPoints[step].y - textVerticalOffset
            )
        )
    }

    for (line in labelsEndPoints.indices) {
        drawText(
            textMeasurer = textMeasurer,
            text = radarLabels[line],
            style = labelsStyle,
            topLeft = Offset(
                labelsEndPoints[line].x - 50.toDp().toPx(),
                labelsEndPoints[line].y
            )
        )
    }

}