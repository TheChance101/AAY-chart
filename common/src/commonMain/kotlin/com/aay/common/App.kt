package com.aay.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun App() {

    /** Sample of Line Chart **/
//    LineChartSample()

    /** Sample of Bar Chart **/
//    BarChartSample()

    /** Sample of Pie Chart **/
//    PieChartSample()

    TrianglesFromLines(5, 5)

}

data class RadarChartConfig(
    val endPoints: List<Offset>,
    val nextEndPoints: List<Offset>,
    val nextStartPoints: List<Offset>,
)


//fun getRadarConfig(size : Size, numLines: Int, scalarSteps: Int): RadarChartConfig {
//
//
//    return RadarChartConfig(
//
//    )
//}


@Composable
fun TrianglesFromLines(numLines: Int, scalarSteps: Int) {
    Canvas(
        modifier = Modifier.fillMaxSize(),
    ) {

        val center = Offset(size.width / 2, size.height / 2)
        val angleBetweenLines = 2 * PI / numLines
        val radius = 500f

        for (i in 0 until numLines) {

            val angle = i * angleBetweenLines
            val nextIndex = (i + 1) % numLines

            val nextAngle = nextIndex * angleBetweenLines
            val value = radius / scalarSteps

            val endPoint = getCircumferencePointOffset(center, radius, angle)

            drawLine(
                color = Color.Gray,
                start = center,
                end = endPoint,
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            for (step in 1 until scalarSteps + 1) {
                val startEndPoint = getCircumferencePointOffset(center, value * step, angle)
                val nextEndPoint = getCircumferencePointOffset(center, value * step, nextAngle)

                drawLine(
                    color = Color.Gray,
                    start = startEndPoint,
                    end = nextEndPoint,
                    strokeWidth = 2f,
                    cap = StrokeCap.Round
                )
            }

        }
    }
}

@Composable
fun AxisDate(value: Int, unit: String, numLines: Int, scalarSteps: Int, offest: List<Offset>) {

}

private fun getCircumferencePointOffset(
    center: Offset,
    radius: Float,
    nextAngle: Double
) = Offset(
    center.x + radius * cos(nextAngle).toFloat(),
    center.y + radius * sin(nextAngle).toFloat()
)







