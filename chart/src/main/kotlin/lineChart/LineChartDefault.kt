package lineChart

import androidx.compose.ui.graphics.Color
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
    val backGroundColor= Color.Transparent
    val xAxisData= listOf("2015","2016","2017","2018","2019")
    val anmiteChart = true
}