package com.aay.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.ringChart.RingChart
import com.aay.compose.ringChart.model.RingChartData

@Composable
fun RingChartSample() {
    val restartAnimationTest = remember { mutableStateOf(0) }

    key(restartAnimationTest.value) {
        val ringChartData = listOf(
            RingChartData(
                name = "Move",
                value = 450.0,
                maxValue = 600.0,
                color = Color(0xFFFF3B30)
            ),
            RingChartData(
                name = "Exercise",
                value = 25.0,
                maxValue = 30.0,
                color = Color(0xFF4CD964)
            ),
            RingChartData(
                name = "Stand",
                value = 12.0,
                maxValue = 12.0,
                color = Color(0xFF5AC8FA)
            ),
            RingChartData(
                name = "Sleep",
                value = 6.0,
                maxValue = 8.0,
                color = Color(0xFF007AFF)
            ),
            RingChartData(
                name = "Water",
                value = 2.0,
                maxValue = 3.0,
                color = Color(0xFF5856D6)
            ),
            RingChartData(
                name = "Meditation",
                value = 1.0,
                maxValue = 2.0,
                color = Color(0xFFFF9500)
            ),
            RingChartData(
                name = "Reading",
                value = 0.5,
                maxValue = 1.0,
                color = Color(0xFFFFCC00)
            ),
            RingChartData(
                name = "Writing",
                value = 0.2,
                maxValue = 0.5,
                color = Color(0xFFAF52DE)
            ),
            RingChartData(
                name = "Drawing",
                value = 0.1,
                maxValue = 0.2,
                color = Color(0xFF5856D6)
            ),
        )

        RingChart(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            ringChartData = ringChartData,
            ringWidth = 0.15f,
            ringGap = 0.03f,
            legendPosition = LegendPosition.BOTTOM
        )
       Box(
           modifier = Modifier.fillMaxSize(),
           contentAlignment = Alignment.TopCenter
       ) {
           Button(
               onClick = { restartAnimationTest.value++ },
               colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
           ) {
               Text("Restart Animation", color = Color.White)
           }
       }
    }
}

/**
 * Calculates the average percentage completion across all rings.
 */
private fun calculateTotalPercentage(ringChartData: List<RingChartData>): Int {
    val totalPercentage = ringChartData.sumOf { (it.value / it.maxValue).coerceIn(0.0, 1.0) } / ringChartData.size
    return (totalPercentage * 100).toInt()
}
