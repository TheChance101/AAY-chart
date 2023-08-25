package com.aay.compose.radarChart

import androidx.compose.ui.geometry.Offset

data class RadarChartConfig(
    val center: Offset,
    val endPoints: List<Offset>,
    val nextEndPoints: List<Offset>,
    val nextStartPoints: List<Offset>,
    val scalarPoints: List<Offset>,
    val labelsPoints: List<Offset>
)