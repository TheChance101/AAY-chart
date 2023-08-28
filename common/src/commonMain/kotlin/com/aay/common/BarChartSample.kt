package com.aay.common

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
            dataName = "Completed",
            data = listOf(54.0, 50.6,40.0, 50.6,40.0, 50.6,40.0),
            barColor = Color.Blue,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(30.0, 80.6,50.0, 10.6,40.0, 60.6,60.0),
            barColor = Color.Black,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(50.0, 70.6,30.0, 40.6,50.0, 50.6,70.0),
            barColor = Color.LightGray,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(0.0, 50.6,80.0, 55.6,40.0, 10.6,90.0),
            barColor = Color.Magenta,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(70.0, 50.6,70.0, 60.6,50.0, 30.6,80.0),
            barColor = Color.Green,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(70.0, 50.6,60.0, 50.6,80.0, 60.6,70.0),
            barColor = Color.Gray,
        ),
    )

    Box(Modifier.padding(24.dp).fillMaxSize()) {
        BarChart(
            chartParameters = testBarParameters,
            gridColor = Color.DarkGray,
            xAxisData = listOf("2016", "2017","2018","2019","2020","2021"),
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
        )
    }
}