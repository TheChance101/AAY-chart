package lineChart.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lineChart.model.BackGroundGrid

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.chartContainer(
    xAxisData: List<T>,
    textMeasure: TextMeasurer,
    upperValue: Dp,
    lowerValue: Dp,
    isShowBackgroundLines: BackGroundGrid,
    backGroundLineColor: Color,
    backgroundLineWidth: Float,
    pathEffect: PathEffect,
    spacingX:Dp,
    spacingY:Dp,
) {
    xAxisDrawing(xAxisData, spacingX, textMeasure)
    yAxisDrawing(upperValue, lowerValue, textMeasure,spacingY)
    backgroundLine(
        xAxisDataSize = xAxisData.size,
        isShowBackgroundLines = isShowBackgroundLines,
        backGroundColor = backGroundLineColor,
        backgroundLineWidth = backgroundLineWidth,
        pathEffect = pathEffect,
        spacingX=spacingX,
        spacingY=spacingY,
    )
}