package lineChart.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import lineChart.model.BackGroundGrid

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.chartContainer(
    xAxisData: List<T>,
    spacing: Dp,
    textMeasure: TextMeasurer,
    upperValue: Float,
    lowerValue: Float,
    isShowBackgroundLines: BackGroundGrid,
    backGroundLineColor: Color,
    backgroundLineWidth: Float

) {
    xAxisDrawing(xAxisData, spacing, textMeasure)
    yAxisDrawing(upperValue, lowerValue, textMeasure)
    backgroundLine(
        xAxisData,
        isShowBackgroundLines = isShowBackgroundLines,
        spacing = spacing,
        backGroundColor = backGroundLineColor,
        backgroundLineWidth = backgroundLineWidth
    )
}