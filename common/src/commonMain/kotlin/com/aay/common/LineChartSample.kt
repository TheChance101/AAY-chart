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
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType

@Composable
fun LineChartSample() {

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "revenue",
            data = listOf(7000000.0, 00.0, 50000000.33, 40000000.0, 100000000.500, 50000000.0),
            lineColor = Color.Gray,
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
        ),
        LineParameters(
            label = "Earnings",
            data = listOf(60000000.0, 80000000.6, 40000000.33, 86000000.232, 88000000.0, 90000000.0),
            lineColor = Color(0xFFFF7F50),
            lineType = LineType.DEFAULT_LINE,
            lineShadow = true
        ),
        LineParameters(
            label = "Earnings",
            data = listOf(1000000.0, 40000000.0, 11000000.33, 55000000.23, 1000000.0, 100000000.0),
            lineColor = Color(0xFF81BE88),
            lineType = LineType.CURVED_LINE,
            lineShadow = false,
        )
    )

    Box(Modifier.padding(top = 16.dp, start = 16.dp, bottom = 16.dp)) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = testLineParameters,
            isGrid = true,
            gridColor = Color.Gray,
            xAxisData = listOf("2015", "2016", "2017", "2018", "2019", "2020"),
            animateChart = true,
            showGridWithSpacer = false,
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
            gridOrientation = GridOrientation.GRID,
            legendPosition = LegendPosition.TOP
        )
    }
}