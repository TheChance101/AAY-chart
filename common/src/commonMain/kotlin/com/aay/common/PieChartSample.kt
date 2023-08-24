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
            partName = "Amnah.a",
            data = 500f,
            color = Color(0xFFF2BD00),
        ),
        PieChartData(
            partName = "Amnah",
            data = 700f,
            color = Color(0xFF81BE88),
        ),
        PieChartData(
            partName = "Mohammed",
            data = 500f,
            color = Color(0xFFFF7F50),
        ),
        PieChartData(
            partName = "Yassen",
            data = 500f,
            color = Color(0xFF81BE88),
        ),
        PieChartData(
            partName = "Andrew",
            data = 600f,
            color = Color.Magenta.copy(alpha = .5f),
        ),
    )

    PieChart(
        modifier = Modifier.wrapContentSize(),
        pieChartData = testPieChartData,
        centerTitle = "Orders",
    )

}