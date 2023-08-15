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
            data = listOf(0.0, 20.6, 68886.33, 9999.9, 1000.232, 50.0),
            lineColor = Color.Blue,
        ),
        BarParameters(
            dataName = "Customers",
            data = listOf(0.0, 20.6, 444.33, 9999.9, 876.232, 50.0),
            lineColor = Color.Red,
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
            )
        )
    }
}