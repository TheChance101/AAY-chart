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
//        BarParameters(
//            dataName = "Completed",
//            data = listOf(54.0, 50.6),
//            barColor = Color.Blue,
//        ),
//        BarParameters(
//            dataName = "Completed",
//            data = listOf(30.0, 20.6),
//            barColor = Color.Black,
//        ),
//        BarParameters(
//            dataName = "Completed",
//            data = listOf(50.0, 70.6),
//            barColor = Color.LightGray,
//        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(400.0, 1000.6,800.0, 550.6,444.0, 100.6,100.0),
            barColor = Color.Magenta,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(700.0, 500.6,707.0, 699.6,500.0, 300.6,800.0),
            barColor = Color.Green,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(100.0, 999.6,600.0, 800.6,150.0, 100.6,0.99),
            barColor = Color.Gray,
        ),
    )

    Box(Modifier.padding(24.dp).fillMaxSize()) {
        BarChart(
            chartParameters = testBarParameters,
            gridColor = Color.DarkGray,
            xAxisData = listOf("2016", "2017", "2018","2019","2020", "2021","2022"),
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