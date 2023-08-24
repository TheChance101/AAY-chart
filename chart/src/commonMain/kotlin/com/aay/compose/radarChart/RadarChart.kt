package com.aay.compose.radarChart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer

@OptIn(ExperimentalTextApi::class)
@Composable
fun RadarChart(
    numLines: Int,
    scalarSteps: Int,
    scalarValue: Double,
    radarLabels: List<String>,
    values: List<Double>,
    values2: List<Double>
) {

    val textMeasurer = rememberTextMeasurer()
    Box(modifier = Modifier.fillMaxSize().drawBehind {
        val radius = (size.minDimension / 2) - 30f
        val radarChartConfig = getRadarConfig(size, numLines, scalarSteps)
        val polygonEndPoints =
            getPolygonShapeEndPoints(values2, values, radius, scalarValue, Offset(size.width / 2, size.height / 2))

        drawRadarNet(radarChartConfig)
        val path = Path().apply {
            drawPolygon(polygonEndPoints[0])
        }
        val path2 = Path().apply {
            drawPolygon(polygonEndPoints[1])
        }
        drawPath(path, color = Color(0xffe6ffd6), style = Stroke(2f))
        drawPath(path, color = Color(0xffc2ff86), style = Fill, alpha = 0.5f)
        drawPath(path2, color = Color(0xffFF8B99), style = Stroke(2f))
        drawPath(path2, color = Color(0xffFFDBDE), style = Fill, alpha = 0.5f)
        drawAxisData(textMeasurer, radarChartConfig, radarLabels, scalarValue, scalarSteps)

    })

}