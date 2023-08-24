package com.aay.common

import androidx.compose.runtime.Composable
import com.aay.compose.radarChart.RadarChart

@Composable
fun RadarChartSample() {
    val list = listOf("Party A", "Party B", "Party C", "PartD", "Party E", "Party F ", "Party G", "Party H", "Party I")
    val values2 = listOf(120.0, 160.0, 110.0, 112.0, 210.0, 120.0, 215.0, 101.0, 215.0)
    val values = listOf(220.0, 180.0, 165.0, 135.0, 120.0, 150.0, 215.0, 190.0, 220.0)
    RadarChart(
        radarLabels = list,
        numLines = 9,
        scalarSteps = 5,
        scalarValue = 200.0,
        values = values,
        values2 = values2
    )
}
