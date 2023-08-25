package com.aay.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters

@Composable
fun BarChartSample() {

    val testBarParameters: List<BarParameters> = listOf(
        BarParameters(
            dataName = "Revenue",
            data = listOf(50.0, 70.6, 90.33, 70.9, 20.232, 90.0),
            barColor = Color(0xFFF2BD00),
        ),
        BarParameters(
            dataName = "Customers",
            data = listOf(77.0, 20.6, 44.33, 10.9, 50.232, 22.0),
            barColor = Color.Blue.copy(alpha = .5f),
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(90.0, 50.6, 30.33, 40.9, 60.232, 70.0),
            barColor = Color(0xFF81BE88),
        ),
        BarParameters(
            dataName = "Sales",
            data = listOf(30.0, 10.6, 20.33, 55.9, 40.232, 66.0),
            barColor = Color.Black,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(90.0, 50.6, 30.33, 40.9, 60.232, 70.0),
            barColor = Color.Blue,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(90.0, 50.6, 30.33, 40.9, 60.232, 70.0),
            barColor = Color.White,
        ),
    )

    Box(Modifier.padding(24.dp).background(Color.Blue.copy(0.5f)).wrapContentSize()) {
        BarChart(
            chartParameters = testBarParameters,
            gridColor = Color.DarkGray,
            xAxisData = listOf("2015", "2016", "2017", "2018", "2019", "2020"),
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.W400
            ),
            barWidthPx = 15.dp,
            boxSize = 700.dp
        )
    }
}