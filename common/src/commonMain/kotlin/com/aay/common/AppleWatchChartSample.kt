//package com.aay.common
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//
//@Composable
//fun AppleChartSample() {
//    val appleChartParameters = listOf(
//        AppleChartParameters(
//            label = "fit",
//            data = 65f,
//            color = Color(0xfe0a8b)
//        ),
//        AppleChartParameters(
//            label = "fit",
//            data = 50f,
//            color = Color(0xA1FF00)
//        ),
//        AppleChartParameters(
//            label = "fit",
//            data = 55f,
//            color = Color(0x1AD6DE)
//        )
//    )
//    Box(modifier = Modifier.fillMaxSize()) {
//        AppleWatchChart(
//            appleChartParameters = appleChartParameters
//        )
//    }
//}