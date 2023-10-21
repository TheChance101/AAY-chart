package com.aay.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aay.compose.appleWatchChart.AppleWatchChart
import com.aay.compose.appleWatchChart.model.AppleChartParameters

@Composable
fun AppleChartSample() {
    val appleChartParameters = listOf(
        AppleChartParameters(
            label = "fit",
            data = 65f,
            color = Color(0xfffe0a8b)
        ),
        AppleChartParameters(
            label = "fit",
            data = 55f,
            color = Color(0xFFA1FF00)
        ),
        AppleChartParameters(
            label = "fit",
            data = 55f,
            color = Color(0xFF1AD6DE)
        )
    )
    Box(modifier = Modifier.fillMaxSize()) {
        AppleWatchChart(
            appleChartParameters = appleChartParameters
        )
    }
}