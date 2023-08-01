package model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow


data class LineParameters(
    val dataName : String,
    val data :List<Pair<String,Double>>,
    val lineColor :Color,
    val lineType:LineType,
    val lineShadow:LineShadow,
)

