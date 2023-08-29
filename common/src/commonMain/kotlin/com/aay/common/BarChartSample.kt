package com.aay.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
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
        BarParameters(
            dataName = "Completed",
            data = listOf(50.0, 90.6,80.0,77.6,50.0, 20.6,10.0),
            barColor = Color.LightGray,
        ),
        BarParameters(
            dataName = "Completed",
            data = listOf(50.0, 90.6,80.0,77.6,50.0, 20.6,10.0),
            barColor = Color.Red.copy(0.8f),
        ),
        BarParameters(
            dataName = "andrew",
            data = listOf(40.0, 30.6,20.0, 15.6,70.0, 80.6,60.0),
            barColor = Color.Black.copy(0.8f),
        ),
        BarParameters(
            dataName = "amnah",
            data = listOf(20.0, 15.6,30.0, 50.6,70.0, 90.6,70.0),
            barColor = Color.Gray.copy(0.8f,0.9F),
        ),
    )

    Box(Modifier.fillMaxSize()) {
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
            barWidth = 30.dp,
            spaceBetweenBars = 50.dp,
            spaceBetweenGroups = 100.dp
        )
    }
}