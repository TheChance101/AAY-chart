package com.aay.common

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
            data = listOf(500.0, 400.6, 500.33, 40.0, 100.232, 300.0, 1.0, 500.0, 500.0),
            lineColor = Color.Gray,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = true,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(300.0, 80.6, 40.33, 86.232, 88.0, 300.0, 80.0, 500.0, 300.0),
            lineColor = Color(0xFFFF7F50),
            lineType = LineType.DEFAULT_LINE,
            lineShadow = true
        )
    )

    Box(Modifier.padding(24.dp)) {
        LineChart(
            modifier = Modifier.size(500.dp),
            linesParameters = testLineParameters,
            gridColor = Color.DarkGray,
            xAxisData = listOf("2015", "2016", "2017", "2018", "2019", "2020", "2030", "2050", "2002"),
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
            ),
            yAxisRange = 14,
            oneLineChart = false,
            gridOrientation = Orientation.Horizontal
        )
    }
}