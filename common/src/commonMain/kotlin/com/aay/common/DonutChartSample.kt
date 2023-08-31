package com.aay.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.model.PieChartData

@Composable
fun DonutChartSample() {

    val testPieChartData: List<PieChartData> = listOf(
        PieChartData(
            partName = "Aziza",
            data = 500.0,
            color = Color(0xFFF2BD00),
        ),
        PieChartData(
            partName = "Amnah",
            data = 700.0,
            color = Color(0xFF81BE88),
        ),
        PieChartData(
            partName = "Mohammed",
            data = 500.0,
            color = Color(0xFFFF7F50),
        ),
        PieChartData(
            partName = "Yassen",
            data = 500.0,
            color = Color(0xFF81BE88),
        ),
        PieChartData(
            partName = "Andrew",
            data = 600.0,
            color = Color.Magenta.copy(alpha = .5f),
        ),
        PieChartData(
            partName = "Aziza",
            data = 500.0,
            color = Color(0xFFF2BD00),
        ),
        PieChartData(
            partName = "Amnah",
            data = 700.0,
            color = Color(0xFF81BE88),
        ),
        PieChartData(
            partName = "Mohammed",
            data = 500.0,
            color = Color(0xFFFF7F50),
        ),
        PieChartData(
            partName = "Yassen",
            data = 500.0,
            color = Color(0xFF81BE88),
        ),
        PieChartData(
            partName = "Andrew",
            data = 600.0,
            color = Color.Magenta.copy(alpha = .5f),
        ),
        PieChartData(
            partName = "Aziza",
            data = 500.0,
            color = Color(0xFFF2BD00),
        ),
        PieChartData(
            partName = "Amnah",
            data = 700.0,
            color = Color(0xFF81BE88),
        ),
        PieChartData(
            partName = "Mohammed",
            data = 500.0,
            color = Color(0xFFFF7F50),
        ),
        PieChartData(
            partName = "Yassen",
            data = 500.0,
            color = Color(0xFF81BE88),
        ),
        PieChartData(
            partName = "Andrew",
            data = 600.0,
            color = Color.Magenta.copy(alpha = .5f),
        ),
    )

    DonutChart(
        modifier = Modifier.fillMaxSize(),
        pieChartData = testPieChartData,
        centerTitle = "Orders",
        centerTitleStyle = TextStyle(color = Color.Gray),
        outerCircularColor = Color.LightGray,
        innerCircularColor = Color.Gray,
        ratioLineColor = Color.LightGray,
    )

}