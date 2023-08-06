package lineChart.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import lineChart.model.BackGroundGrid


fun DrawScope.backgroundLine(
    xAxisDataSize: Int,
    isShowBackgroundLines: BackGroundGrid,
    spacing: Dp,
    backGroundColor: Color,
    backgroundLineWidth: Float
) {
    // Calculate the valid boundaries of the chart area
    val minX = spacing.toPx()
    val xAxisMaxValue = size.width + xAxisDataSize

    val yAxisList = mutableListOf<Float>()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 16f), 0f)


    // Draw background lines
    if (isShowBackgroundLines == BackGroundGrid.SHOW) {
        (0..5).forEach { i ->
            yAxisList.add(size.height - spacing.toPx()- i * size.height / 8f)
            val yAlignmentValue = yAxisList[i] + 65f

            // Ensure the line stays within the boundaries
            val xStart = minX - 80
            val xEnd = (size.width).coerceIn(minX, xAxisMaxValue - spacing.toPx())

            drawLine(
                backGroundColor,
                start = Offset(xStart, yAlignmentValue),
                end = Offset(xEnd, yAlignmentValue),
                strokeWidth = backgroundLineWidth,
                pathEffect = pathEffect
            )
        }
    }
}