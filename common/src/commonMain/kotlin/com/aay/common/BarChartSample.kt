package com.aay.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
            data = listOf(50.0, 70.6),
            barColor = Color(0xFFF2BD00),
        ),
        BarParameters(
            dataName = "Customers",
            data = listOf(77.0, 20.9),
            barColor = Color.Red,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(40.0, 50.6, 30.33, 40.9, 60.232, 70.0),
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
            xAxisData = listOf("2015", "2016", "2017", "2018"),
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