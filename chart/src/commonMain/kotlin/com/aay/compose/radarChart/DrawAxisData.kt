package com.aay.compose.radarChart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawAxisData(
    textMeasurer: TextMeasurer,
    radarChartConfig: RadarChartConfig,
    radarLabels: List<String>,
    scalarValue: Double,
    scalarSteps: Int
) {

    val labelsEndPoints = radarChartConfig.labelsPoints
    val nextStartPoints = radarChartConfig.scalarPoints.toMutableList()
    nextStartPoints.add(0, center)
    nextStartPoints.removeAt(nextStartPoints.size - 1)

    val scalarStep = scalarValue / (scalarSteps - 1)
    val textVerticalOffset = 10.toDp().toPx()

    for (step in 0 until scalarSteps) {
        drawText(
            textMeasurer = textMeasurer,
            text = (scalarStep * step).toString(),
            style = TextStyle(
                color = Color.Black,
                fontSize = 8.sp
            ),
            topLeft = Offset(
                nextStartPoints[step].x,
                nextStartPoints[step].y - textVerticalOffset
            )
        )
    }

    for (line in labelsEndPoints.indices) {
        drawText(
            textMeasurer = textMeasurer,
            text = radarLabels[line],
            style = TextStyle(
                color = Color.Black,
                fontSize = 12.sp
            ),
            topLeft = Offset(
                labelsEndPoints[line].x - 10.toDp().toPx(),
                labelsEndPoints[line].y
            )
        )
    }
}