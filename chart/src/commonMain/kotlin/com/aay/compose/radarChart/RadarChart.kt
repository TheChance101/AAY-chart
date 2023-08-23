package com.aay.compose.radarChart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer

@OptIn(ExperimentalTextApi::class)
@Composable
fun RadarChart(
    numLines: Int,
    scalarSteps: Int,
    scalarValue: Double,
    radarLabels: List<String>,
) {
    val textMeasurer = rememberTextMeasurer()

    Box(modifier = Modifier.fillMaxSize().drawBehind {
        val radarChartConfig = getRadarConfig(size, numLines, scalarSteps)
        drawRadarNet(radarChartConfig)
        drawAxisData(textMeasurer, radarChartConfig, radarLabels, scalarValue, scalarSteps)
    })

}