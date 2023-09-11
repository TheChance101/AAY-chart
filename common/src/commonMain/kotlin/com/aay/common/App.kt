package com.aay.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {

    /** Sample of Line Chart **/
//    LineChartSample()


    /** Sample of Bar Chart **/
//    BarChartSample()

    /** Sample of Donut Chart **/
//    DonutChartSample()

    /** sample of Pie Chart **/

    /** Sample of Radar Chart **/
    Column {
        RadarChartSample(modifier = Modifier.weight(1f))
//        RadarChartSample(modifier = Modifier.weight(1f))
//        PieChartSample()
        PieChartSample(modifier = Modifier.size(200.dp))
//        PieChartSample()
    }
}