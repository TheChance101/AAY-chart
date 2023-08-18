package com.aay.compose.barChart.model

import androidx.compose.ui.graphics.Color

data class BarParameters(
    val dataName: String,
    val data: List<Double>,
    val barColor: Color,
)

