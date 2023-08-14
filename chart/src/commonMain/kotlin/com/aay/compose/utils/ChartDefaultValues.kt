package com.aay.compose.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType

object ChartDefaultValues {

    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "revenue",
            data = emptyList(),
            lineColor = Color.Blue,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = true,
        )
    )

    val barParameters: List<BarParameters> = listOf(
        BarParameters(
            dataName = "revenue",
            data = emptyList(),
            lineColor = Color.Blue,
        )
    )

    const val IS_SHOW_GRID = true
    val gridColor = Color.Gray
    const val ANIMATED_CHART = true
    val backgroundLineWidth = 1.dp
    const val SHOW_BACKGROUND_WITH_SPACER = true
    const val chartRatio = 0f
    val descriptionDefaultStyle = TextStyle(
        color = Color.Black,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400
    )

    val headerArrangement = Arrangement.spacedBy(24.dp)
    val axesStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.Gray,
    )

}