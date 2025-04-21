package com.aay.compose.radarChart.model

import androidx.compose.ui.geometry.Offset

internal data class RadarChartConfig(
    val center: Offset,
    val netCornersPoints: List<Offset>,
    val stepsEndPoints: List<Offset>,
    val stepsStartPoints: List<Offset>,
    val scalarValuesPoints: List<Offset>,
    val labelsPoints: List<Offset>
)