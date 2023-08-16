package com.aay.common

import androidx.compose.runtime.Composable
import com.aay.compose.wormChart.WormChart

@Composable
fun WormChartSample(){
    WormChart(
        listOf(20.0,50.0,10.0,20.0,0.0),
    )
}