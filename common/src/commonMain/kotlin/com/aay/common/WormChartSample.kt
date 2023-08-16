package com.aay.common

import androidx.compose.runtime.Composable
import com.aay.compose.wormChart.WormChart

@Composable
fun WormChartSample(){
    WormChart(
        listOf(120.0,80.0,50.0,60.0,0.0),
    )
}