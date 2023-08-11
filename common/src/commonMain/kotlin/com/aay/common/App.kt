package com.aay.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.BackGroundGrid
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineShadow
import com.aay.compose.lineChart.model.LineType

@Composable
fun App() {
    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "revenue",
            data = listOf(0.0, 20.6, 6888888886.33, 20000000.0, 11000000.232, 50.0),
            lineColor = Color.Blue,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = LineShadow.BLANK,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(0.0, 16000.6, 40.33, 9100000000.232, 88.0, 30.0),
            lineColor = Color.Black,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.SHADOW,
        ),
        LineParameters(
            dataName = "Earnings",
            data = listOf(0.0, 4000000000.0, 11.33, 55.232, 00.0, 100.0),
            lineColor = Color.Red,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.SHADOW,
        )
    )

    Box(Modifier.padding(24.dp)) {
        LineChart(
            modifier = Modifier.height(350.dp),
            linesParameters = testLineParameters,
            backGroundColor = Color.Blue,
            xAxisData = listOf("2015", "2016", "2017", "2018", "2019", "2020"),
            showBackgroundGrid = BackGroundGrid.SHOW,
            animateChart = true,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 11f), 0f),
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
