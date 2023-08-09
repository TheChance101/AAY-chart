package com.aay.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aay.compose.lineChart2.LineChart
import com.aay.compose.lineChart2.model.BackGroundGrid
import com.aay.compose.lineChart2.model.LineParameters
import com.aay.compose.lineChart2.model.LineShadow
import com.aay.compose.lineChart2.model.LineType


@Composable
fun App() {
    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "revenue",
            data = listOf(1000.6, 2000.6, 6677.33, 99983.232, 11111.232),
            lineColor = Color.Blue,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.BLANK,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(188900.6, 87100.6, 89900.33, 91111.232),
            lineColor = Color.Black,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = LineShadow.SHADOW,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(188900.6, 67788.6, 1111111.33, 5555.232),
            lineColor = Color.Red,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = LineShadow.SHADOW,
        )
    )

    Box(Modifier.padding(24.dp)) {
        LineChart(
            modifier = Modifier.size(700.dp),
            linesParameters = testLineParameters,
            backGroundColor = Color.Blue,
            xAxisData = listOf("2015", "2016", "2017", "2018", "2019"),
            showBackgroundGrid = BackGroundGrid.SHOW,
            animateChart = true
        )
    }
}