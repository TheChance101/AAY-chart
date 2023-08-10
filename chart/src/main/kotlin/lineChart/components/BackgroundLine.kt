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
    backGroundColor: Color,
    backgroundLineWidth: Float,
    pathEffect: PathEffect,
    spacingX:Dp,
    spacingY: Dp
) {
    // Calculate the valid boundaries of the chart area
    val minX = spacingX.toPx()
    val xAxisMaxValue = size.width + xAxisDataSize

    val yAxisList = mutableListOf<Float>()

    // Draw background lines
    if (isShowBackgroundLines == BackGroundGrid.SHOW) {
        (0..6).forEach { i ->
            yAxisList.add((size.height.toDp() - spacingY - (i * size.height).toDp() / 7).toPx())
            val yAlignmentValue = yAxisList[i] + 5.dp.toPx()

            // Ensure the line stays within the boundaries
            val xStart = minX
            val xEnd = (size.width).coerceAtMost(xAxisMaxValue - spacingX.toPx().div(1.2f)).coerceAtLeast(minX)

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