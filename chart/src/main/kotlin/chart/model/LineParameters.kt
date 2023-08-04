package chart.model

import androidx.compose.ui.graphics.Color

data class LineParameters(
    val dataName: String,
    val data: List<Double>,
    val lineColor: Color,
    val lineType: LineType,
    val lineShadow: LineShadow,
)

