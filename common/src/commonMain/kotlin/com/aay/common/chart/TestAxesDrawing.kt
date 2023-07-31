package com.aay.common.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestAxesDrawing(){
    val revenueData = listOf(
        Pair("Jan",10000),
        Pair("Feb",20000),
        Pair("Mar",40000),
        Pair("Apr",50000),
        Pair("May",60000),
        Pair("Aug",70000),
        Pair("Sep",80000),
        Pair("Oct",90000),

        )
    Column (modifier = Modifier.fillMaxSize()) {
        AxesDrawing(
            modifier = Modifier.size(500.dp).align(Alignment.CenterHorizontally),
            data = revenueData,
            getXLabel = { month -> month },
            getYLabel = { revenue -> "\$${revenue}" }
        )
    }
}