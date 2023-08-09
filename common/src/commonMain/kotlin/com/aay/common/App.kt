package com.aay.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.BackGroundGrid
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineShadow
import com.aay.compose.lineChart.model.LineType

@Composable
fun App() {
    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "Earnings",
            data = listOf(10.6, 8.6, 80.33, 91.232),
            lineColor = Color.Yellow,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.SHADOW,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(18.6, 67.6, 11.33, 55.232),
            lineColor = Color.Red,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.SHADOW,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(50.6, 200.6, 1.33, 55.232),
            lineColor = Color.Blue,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.SHADOW,
        ) ,
        LineParameters(
            dataName = "Earnings",
            data = listOf(50.6, 20.6, 11.33, 55.232),
            lineColor = Color.Green,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.SHADOW,
        )
    )

    Box(Modifier.padding(24.dp)) {
        LineChart(
            modifier = Modifier.size(700.dp),
            linesParameters = testLineParameters,
            backGroundColor = Color.Gray,
            xAxisData = listOf("2015", "2016", "2017", "2018", "2019"),
            showBackgroundGrid = BackGroundGrid.SHOW,
            animateChart = true
        )
    }
}
