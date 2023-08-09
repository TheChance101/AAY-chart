package com.aay.compose.lineChart2

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.lineChart2.model.BackGroundGrid
import com.aay.compose.lineChart2.model.LineParameters
import com.aay.compose.lineChart2.model.LineShadow
import com.aay.compose.lineChart2.model.LineType

object LineChartDefault {

    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "revenue",
            data = emptyList(),
            lineColor = Color.Blue,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.BLANK,
        )
    )
    val backGroundGrid = BackGroundGrid.SHOW
    val backGroundColor = Color.Gray
    val xAxisData = listOf("2015", "2016", "2017", "2018", "2019")
    const val ANIMATED_CHART = true
    val backgroundLineWidth = 1.dp
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(1f, 1f), 0f)
    val descriptionDefaultStyle = TextStyle(
        color = Color.Black,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400
    )
}