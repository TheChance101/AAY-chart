package com.aay.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun App() {

    /** Sample of Line Chart **/
//    LineChartSample()


    /** Sample of Bar Chart **/
//    BarChartSample()

    /** Sample of Donut Chart **/
//    DonutChartSample()

    /** sample of Pie Chart **/
//    PieChartSample()

    /** Sample of Radar Chart **/
    Column {
//        LineChartSample()
        RadarChartSample(modifier = Modifier.weight(1f).fillMaxSize())
        PieChartSample()
    }

}