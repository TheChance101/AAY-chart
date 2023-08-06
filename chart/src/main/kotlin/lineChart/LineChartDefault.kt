package lineChart

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import lineChart.model.*

object LineChartDefault {

    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            dataName = "revenue",
            data = emptyList(),
            lineColor = Color.Blue,
            lineType = LineType.QUADRATIC_LINE,
            lineShadow = LineShadow.BLANK,
        )
    )
    val backGroundGrid = BackGroundGrid.SHOW
    val backGroundColor= Color.Gray
    val xAxisData= listOf("2015","2016","2017","2018","2019")
    const val ANIMATED_CHART = true
    val backgroundLineWidth = 1.dp
}