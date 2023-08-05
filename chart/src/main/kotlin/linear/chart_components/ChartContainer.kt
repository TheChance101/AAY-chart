package linear.chart_components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.chartContainer(
    xAxisData: List<T>,
    spacing: Dp,
    textMeasure: TextMeasurer,
    upperValue: Float,
    lowerValue: Float,
) {
    xAxisDrawing(xAxisData, spacing, textMeasure)
    yAxisDrawing(upperValue,lowerValue,textMeasure)
}