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
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters

@Composable
fun BarChartSample() {

    val testBarParameters: List<BarParameters> = listOf(
        BarParameters(
            dataName = "revenue",
            data = listOf(50.0, 70.6, 90.33, 70.9, 20.232, 90.0),
            barColor = Color.Blue,
        ),
        BarParameters(
            dataName = "total",
            data = listOf(90.0, 50.6, 30.33, 40.9, 60.232, 70.0),
            barColor = Color.Red,
        ),
        BarParameters(
            dataName = "total",
            data = listOf(30.0, 10.6, 70.33, 55.9, 80.232, 66.0),
            barColor = Color.Black,
        ),
        BarParameters(
            dataName = "total",
            data = listOf(77.0, 20.6, 44.33, 99.9, 50.232, 22.0),
            barColor = Color.Cyan,
        ),
    )

    Box(Modifier.padding(24.dp)) {
        BarChart(
            modifier = Modifier.fillMaxSize(),
            chartParameters = testBarParameters,
            gridColor = Color.Blue,
            xAxisData = listOf("2015", "2016", "2017", "2018", "2019", "2020"),
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Blue,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Blue.copy(alpha = 0.5f),
                fontWeight = FontWeight.W400
            ),
            barWidthPx = 10.dp
        )
    }
}