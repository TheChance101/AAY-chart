package com.aay.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import lineChart.LineChart
import lineChart.model.BackGroundGrid
import lineChart.model.LineParameters
import lineChart.model.LineShadow
import lineChart.model.LineType

@Preview
@Composable
fun AppPreview() {

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "revenue",
            data = listOf(1000.6, 2000.6, 6677.33, 34783.232),
            lineColor = Color.Blue,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.BLANK,
        )
    )

    LineChart(
        modifier = Modifier.size(700.dp),
        linesParameters = testLineParameters,
        backGroundColor = Color.Blue,
        xAxisData = listOf("2015", "2016", "2017", "2018", "2019"),
        showBackgroundGrid = BackGroundGrid.SHOW,
        animateChart = true
    )
}