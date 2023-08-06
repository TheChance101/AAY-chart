package lineChart.components


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import lineChart.model.BackGroundGrid
import lineChart.model.LineParameters

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.chartContainer(
    xAxisData: List<T>,
    spacing: Dp,
    textMeasure: TextMeasurer,
    upperValue: Float,
    lowerValue: Float,
    isShowBackgroundLines: BackGroundGrid,
    backGroundLineColor: Color,
    backgroundLineWidth: Float,
    pathEffect: PathEffect,
    lineParametersList: List<LineParameters>,
    animatedProgress: Animatable<Float, AnimationVector1D>
) {
    xAxisDrawing(xAxisData, spacing, textMeasure)
    yAxisDrawing(upperValue, lowerValue, textMeasure)

    backgroundLine(
        xAxisDataSize = xAxisData.size,
        isShowBackgroundLines = isShowBackgroundLines,
        spacing = spacing,
        backGroundColor = backGroundLineColor,
        backgroundLineWidth = backgroundLineWidth,
        pathEffect = pathEffect
    )

    linesDrawing(
        lineParametersList = lineParametersList,
        xAxisData = xAxisData,
        ratioUpperValue = upperValue,
        ratioLowerValue = lowerValue,
        spacing = spacing,
        animatedProgress = animatedProgress
    )
}