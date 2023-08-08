package lineChart.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lineChart.model.BackGroundGrid


fun DrawScope.backgroundLine(
    xAxisDataSize: Int,
    isShowBackgroundLines: BackGroundGrid,
    spacing: Dp,
    backGroundColor: Color,
    backgroundLineWidth: Float,
    pathEffect: PathEffect
) {
    // Calculate the valid boundaries of the chart area
    val minX = spacing.toPx()
    val xAxisMaxValue = size.width + xAxisDataSize

    val yAxisList = mutableListOf<Float>()

    // Draw background lines
    if (isShowBackgroundLines == BackGroundGrid.SHOW) {
        (0..5).forEach { i ->
            yAxisList.add(size.height - spacing.toPx() - i * size.height / 8f)
            val yAlignmentValue = yAxisList[i] + 11.dp.toPx()

            // Ensure the line stays within the boundaries
            val xStart = minX - 80
            val xEnd = (size.width).coerceAtMost(xAxisMaxValue - spacing.toPx()).coerceAtLeast(minX)

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