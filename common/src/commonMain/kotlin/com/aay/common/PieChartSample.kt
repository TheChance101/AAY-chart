package com.aay.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aay.compose.pieChart.PieChart
import com.aay.compose.pieChart.model.PiePart

@Composable
fun PieChartSample() {

    val testPieParts: List<PiePart> = listOf(
        PiePart(
            partName = "revenue",
            data = 500f,
            color = Color.Black,
        ),
        PiePart(
            partName = "Earnings",
            data = 500f,
            color = Color.Blue,
        ),
        PiePart(
            partName = "Earnings",
            data = 1000f,
            color = Color.Red,
        ),
    )

    PieChart(
        modifier = Modifier.size(height = 500.dp, width = 500.dp),
        pieParts = testPieParts,
        centerTitle = "Orders orders",
        radiusOuter = 200.dp
    )

}