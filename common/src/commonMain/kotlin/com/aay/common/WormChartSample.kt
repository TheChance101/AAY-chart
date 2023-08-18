package com.aay.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.aay.compose.wormChart.WormChart

@Composable
fun WormChartSample(){
    WormChart(
        listOf(120.0,80.0,50.0,60.0,0.0),
        color = Color(0xFFFF7F50)
    )
}