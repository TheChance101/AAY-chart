package com.aay.compose.radarChart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.aay.compose.radarChart.model.RadarChartConfig

private const val SCALAR_VALUES_VERTICAL_DISPLACEMENT = 20
private const val SCALAR_VALUES_HORIZONTAL_DISPLACEMENT = 5

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawScalarValues(
    scalarValuesStyle: TextStyle,
    textMeasurer: TextMeasurer,
    radarChartConfig: RadarChartConfig,
    scalarValue: Double,
    scalarSteps: Int,
    unit: String
) {
    val scalarStep = scalarValue / (scalarSteps - 1)
    val nextStartPoints = radarChartConfig.scalarValuesPoints.toMutableList()
    nextStartPoints.add(0, center)
    nextStartPoints.removeAt(nextStartPoints.size - 1)

    for (step in 0 until scalarSteps) {
        drawText(
            textMeasurer = textMeasurer,
            text = (scalarStep * step).toString() + " " + unit,
            style = scalarValuesStyle,
            topLeft = Offset(
                nextStartPoints[step].x + SCALAR_VALUES_HORIZONTAL_DISPLACEMENT.toDp().toPx(),
                nextStartPoints[step].y - SCALAR_VALUES_VERTICAL_DISPLACEMENT.toDp().toPx()
            )
        )
    }

}