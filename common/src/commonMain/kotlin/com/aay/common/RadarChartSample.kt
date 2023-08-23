package com.aay.common

import androidx.compose.runtime.Composable
import com.aay.compose.radarChart.RadarChart

@Composable
fun RadarChartSample() {
    val list = listOf("Party A", "Party A", "Party A", "Party", "H")
    RadarChart(radarLabels = list, numLines = 5, scalarSteps = 5, scalarValue = 100.0)
}
