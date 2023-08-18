package com.aay.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType

@Composable
fun LineChartSample() {

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "revenue",
            data = listOf(0.0, 20.6, 75.33, 90.9, 45.232, 50.0),
            lineColor = Color.Gray,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = true,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(0.0, 16.6, 40.33, 86.232, 88.0, 30.0),
            lineColor = Color(0xFFFF7F50),
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = true
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(0.0, 40.0, 11.33, 55.232, 00.0, 100.0),
            lineColor = Color.Red,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = false,
        )
    )

    Box(Modifier.padding(24.dp)) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = testLineParameters,
            gridColor = Color.LightGray.copy(alpha = .5f),
            xAxisData = listOf("2015", "2016", "2017", "2018", "2019", "2020"),
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.W400
            )
        )
    }
}