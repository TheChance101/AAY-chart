package com.aay.compose.radarChart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import com.aay.compose.radarChart.model.NetLinesStyle
import com.aay.compose.radarChart.model.Polygon

@OptIn(ExperimentalTextApi::class)
@Composable
fun RadarChart(
    radarLabels: List<String>,
    labelsStyle: TextStyle,
    netLinesStyle: NetLinesStyle,
    scalarSteps: Int,
    scalarValue: Double,
    scalarValuesStyle: TextStyle,
    polygons: List<Polygon>
) {

    val textMeasurer = rememberTextMeasurer()

    validatePolygons(radarLabels, scalarValue, polygons, scalarSteps)

    Canvas(modifier = Modifier.fillMaxSize()) {

        val labelWidth = measureMaxLabelWidth(radarLabels, labelsStyle, textMeasurer)
        val radius = (size.minDimension / 2) - (labelWidth + 10.toDp().toPx())
        val labelRadius = (size.minDimension / 2) - (labelWidth / 2)
        val numLines = radarLabels.size
        val radarChartConfig = calculateRadarConfig(labelRadius, radius, size, numLines, scalarSteps)

        drawRadarNet(netLinesStyle, radarChartConfig)

        polygons.forEach {
            drawPolygonShape(
                this,
                it,
                radius,
                scalarValue,
                Offset(size.width / 2, size.height / 2),
                scalarSteps
            )
        }

        drawAxisData(
            labelsStyle,
            scalarValuesStyle,
            textMeasurer,
            radarChartConfig,
            radarLabels,
            scalarValue,
            scalarSteps,
            polygons[0].unit
        )

    }

}

private fun validatePolygons(
    radarLabels: List<String>,
    scalarValue: Double,
    polygons: List<Polygon>,
    scalarSteps: Int
) {

    if (scalarSteps < 0) {
        throw Exception("Scalar steps must be greater than 0")
    }

    if (scalarValue < 0.0) {
        throw Exception("Scalar value must be greater than 0")
    }

    if (radarLabels.size < 3) {
        throw Exception("Number of radar labels must be greater than 2")
    }

    for (polygon in polygons) {
        if (polygon.values.size != radarLabels.size) {
            throw Exception("Number of polygon values must equal to number of radar labels")
        }
        polygon.values.forEach { value ->
            if (value > scalarValue || value < 0.0) {
                throw Exception("Polygon values must be between 0 and scalar value ($scalarValue)")
            }
        }
    }

}


@OptIn(ExperimentalTextApi::class)
private fun DrawScope.measureMaxLabelWidth(
    radarLabels: List<String>,
    labelsStyle: TextStyle,
    textMeasurer: TextMeasurer
): Float {
    return textMeasurer.measure(
        AnnotatedString(
            text = radarLabels.maxByOrNull { it.length } ?: "",
        ), style = labelsStyle
    ).size.width.toDp().toPx()
}



