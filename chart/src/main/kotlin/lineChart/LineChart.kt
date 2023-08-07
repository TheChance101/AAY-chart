package lineChart

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import lineChart.model.BackGroundGrid
import lineChart.model.LineParameters

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    linesParameters: List<LineParameters> = LineChartDefault.lineParameters,
    backGroundColor: Color = LineChartDefault.backGroundColor,
    xAxisData: List<String> = LineChartDefault.xAxisData,
    showBackgroundGrid: BackGroundGrid = LineChartDefault.backGroundGrid,
    barWidthPx: Dp = LineChartDefault.backgroundLineWidth,
    animateChart: Boolean = LineChartDefault.ANIMATED_CHART,
    pathEffect: PathEffect = LineChartDefault.pathEffect,
    descriptionStyle: TextStyle = LineChartDefault.descriptionDefaultStyle
) {

    Column {

        ChartDescription(
            chartLineDetails = linesParameters,
            descriptionStyle = descriptionStyle
        )

        ChartContent(
            modifier = modifier,
            linesParameters = linesParameters,
            backGroundColor = backGroundColor,
            xAxisData = xAxisData,
            showBackgroundGrid = showBackgroundGrid,
            barWidthPx = barWidthPx,
            animateChart = animateChart,
            pathEffect = pathEffect
        )
    }
}