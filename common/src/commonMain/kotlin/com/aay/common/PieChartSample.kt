package com.aay.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aay.compose.pieChart.PieChart
import com.aay.compose.pieChart.model.PieChartData

@Composable
fun PieChartSample() {

    val testPieChartData: List<PieChartData> = listOf(
        PieChartData(
            partName = "revenue",
            data = 500f,
            color = Color.Black,
        ),
        PieChartData(
            partName = "Earnings",
            data = 500f,
            color = Color.Blue,
        ),
        PieChartData(
            partName = "Earnings",
            data = 500f,
            color = Color.Red,
        ),
        PieChartData(
            partName = "Earnings",
            data = 500f,
            color = Color.Magenta,
        ),
        PieChartData(
            partName = "Earnings",
            data = 500f,
            color = Color.Green,
        ),
        PieChartData(
            partName = "Earnings",
            data = 300f,
            color = Color.Magenta,
        ),
        PieChartData(
            partName = "Earnings",
            data = 200f,
            color = Color.Green,
        ),
        PieChartData(
            partName = "Earnings",
            data = 900f,
            color = Color.Green,
        ),
        PieChartData(
            partName = "Earnings",
            data = 60f,
            color = Color.Magenta,
        ),
        PieChartData(
            partName = "Earnings",
            data = 600f,
            color = Color.Green,
        ),
        PieChartData(
            partName = "Earnings",
            data = 500f,
            color = Color.Green,
        ),
        PieChartData(
            partName = "Earnings",
            data = 100f,
            color = Color.Magenta,
        ),
        PieChartData(
            partName = "Earnings",
            data = 900f,
            color = Color.Green,
        ),

    )

    PieChart(
        modifier = Modifier.wrapContentSize(),
        pieChartData = testPieChartData,
        centerTitle = "Orders orders",
        radiusOuter = 200.dp
    )

}