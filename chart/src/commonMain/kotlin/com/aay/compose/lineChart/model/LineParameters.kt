package com.aay.compose.lineChart.model

import androidx.compose.ui.graphics.Color

data class LineParameters(
    val label: String,
    val data: List<Double>,
    val lineColor: Color,
    val lineType: LineType,
    val lineShadow: Boolean,
)

