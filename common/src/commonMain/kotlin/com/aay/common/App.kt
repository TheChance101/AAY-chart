package com.aay.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType

@Composable
fun App() {

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "revenue",
            data = listOf(0.0, 20.6, 68888886.33, 9999.9, 11000000.232, 50.0),
            lineColor = Color.Blue,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = true,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(0.0, 16000.6, 40.33, 91000000.232, 88.0, 30.0),
            lineColor = Color.Black,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = true
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(0.0, 4000.0, 11.33, 55.232, 00.0, 100.0),
            lineColor = Color.Red,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = false,
        )
    )

    Box(Modifier.padding(24.dp)) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = testLineParameters,
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
