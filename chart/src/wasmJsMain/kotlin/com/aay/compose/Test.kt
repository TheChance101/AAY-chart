package com.aay.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeViewport
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.aay.compose.radarChart.RadarChart
import com.aay.compose.radarChart.model.NetLinesStyle
import com.aay.compose.radarChart.model.Polygon
import com.aay.compose.radarChart.model.PolygonStyle
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        ChartsTestScreen()

    }
}

@Composable
fun ChartsTestScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        // ===== Bar Chart =====
        Text("Bar Chart", fontSize = 18.sp)
        val testBarParameters: List<BarParameters> = listOf(
            BarParameters(
                dataName = "Completed",
                data = listOf(0.6, 10.6, 80.0, 50.6, 44.0, 100.6, 10.0),
                barColor = Color(0xFF6200EE) // Purple
            ),
            BarParameters(
                dataName = "Completed",
                data = listOf(50.0, 30.6, 77.0, 69.6, 50.0, 30.6, 80.0),
                barColor = Color(0xFF03DAC5), // Teal
            ),
            BarParameters(
                dataName = "Completed",
                data = listOf(100.0, 99.6, 60.0, 80.6, 10.0, 100.6, 55.99),
                barColor = Color(0xFFBB86FC), // Light Purple
            ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(vertical = 16.dp)
        ) {
            BarChart(
                chartParameters = testBarParameters,
                gridColor = Color.Gray,
                xAxisData = listOf("2016", "2017", "2018", "2019", "2020", "2021", "2022"),
                isShowGrid = true,
                animateChart = true,
                showGridWithSpacer = true,
                yAxisStyle = TextStyle(fontSize = 14.sp, color = Color.Gray),
                xAxisStyle = TextStyle(fontSize = 14.sp, color = Color.Gray),
                yAxisRange = 15,
                barWidth = 20.dp,
                legendPosition = LegendPosition.TOP,
                barCornerRadius = 4.dp
            )
        }

        // ===== Donut Chart =====
        Text("Donut Chart", fontSize = 18.sp)
        val testPieChartData: List<PieChartData> = listOf(
            PieChartData(
                partName = "Part A",
                data = 500.0,
                color = Color(0xFFFF5722), // Deep Orange
            ),
            PieChartData(
                partName = "Part B",
                data = 700.0,
                color = Color(0xFF8BC34A), // Light Green
            ),
            PieChartData(
                partName = "Part C",
                data = 500.0,
                color = Color(0xFF2196F3), // Blue
            ),
            PieChartData(
                partName = "Part D",
                data = 100.0,
                color = Color(0xFFFFC107), // Amber
            ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(vertical = 16.dp)
        ) {
            DonutChart(
                pieChartData = testPieChartData,
                centerTitle = "Orders",
                centerTitleStyle = TextStyle(color = Color(0xFF9C27B0)), // Purple
                outerCircularColor = Color.LightGray,
                innerCircularColor = Color.Gray,
                ratioLineColor = Color.LightGray,
                legendPosition = LegendPosition.BOTTOM
            )
        }

        // ===== Line Chart =====
        Text("Line Chart", fontSize = 18.sp)
        val testLineParameters: List<LineParameters> = listOf(
            LineParameters(
                label = "Revenue",
                data = listOf(7000000.0, 0.0, 50000000.33, 40000000.0, 100000000.5, 50000000.0),
                lineColor = Color(0xFF4CAF50), // Green
                lineType = LineType.CURVED_LINE,
                lineShadow = true,
            ),
            LineParameters(
                label = "Earnings",
                data = listOf(60000000.0, 80000000.6, 40000000.33, 86000000.23, 88000000.0, 90000000.0),
                lineColor = Color(0xFF607D8B), // Blue Grey
                lineType = LineType.DEFAULT_LINE,
                lineShadow = true
            ),
            LineParameters(
                label = "Earnings",
                data = listOf(1000000.0, 40000000.0, 11000000.33, 55000000.23, 1000000.0, 100000000.0),
                lineColor = Color(0xFFFF9800), // Orange
                lineType = LineType.CURVED_LINE,
                lineShadow = false,
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(vertical = 16.dp)
        ) {
            LineChart(
                linesParameters = testLineParameters,
                isGrid = true,
                gridColor = Color.Gray,
                xAxisData = listOf("2015", "2016", "2017", "2018", "2019", "2020"),
                animateChart = true,
                showGridWithSpacer = false,
                yAxisStyle = TextStyle(fontSize = 14.sp, color = Color.Gray),
                xAxisStyle = TextStyle(fontSize = 14.sp, color = Color.Gray),
                yAxisRange = 14,
                oneLineChart = false,
                gridOrientation = GridOrientation.GRID,
                legendPosition = LegendPosition.TOP
            )
        }

        // ===== Pie Chart =====
        Text("Pie Chart", fontSize = 18.sp)
        val testPieChartData2: List<PieChartData> = listOf(
            PieChartData(
                partName = "Part A",
                data = 500.0,
                color = Color(0xFF9C27B0), // Purple
            ),
            PieChartData(
                partName = "Part B",
                data = 700.0,
                color = Color(0xFFFF9800), // Orange
            ),
            PieChartData(
                partName = "Part C",
                data = 500.0,
                color = Color(0xFF00BCD4), // Teal
            ),
            PieChartData(
                partName = "Part D",
                data = 100.0,
                color = Color(0xFF4CAF50), // Green
            ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(vertical = 16.dp)
        ) {
            PieChart(
                pieChartData = testPieChartData2,
                ratioLineColor = Color.Transparent,
                textRatioStyle = TextStyle(color = Color.Transparent),
                legendPosition = LegendPosition.BOTTOM
            )
        }

        // ===== Radar Chart =====
        Text("Radar Chart", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(vertical = 16.dp)
        ) {

            val radarLabels =
                listOf(
                    "Party A",
                    "Party B",
                    "Party C",
                    "Part D",
                    "Party E",
                    "Party F",
                    "Party G",
                    "Party H",
                    "Party I"
                )
            val values2 = listOf(120.0, 160.0, 110.0, 112.0, 200.0, 120.0, 145.0, 101.0, 200.0)
            val values = listOf(180.0, 180.0, 165.0, 135.0, 120.0, 150.0, 140.0, 190.0, 200.0)
            val labelsStyle = TextStyle(
                color = Color(0xFF333333),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            val scalarValuesStyle = TextStyle(
                color = Color(0xFF333333),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

            RadarChart(
                modifier = Modifier.fillMaxSize(),
                radarLabels = radarLabels,
                labelsStyle = labelsStyle,
                netLinesStyle = NetLinesStyle(
                    netLineColor = Color(0xFFC0C0C0),
                    netLinesStrokeWidth = 2f,
                    netLinesStrokeCap = StrokeCap.Butt
                ),
                scalarSteps = 5,
                scalarValue = 200.0,
                scalarValuesStyle = scalarValuesStyle,
                polygons = listOf(
                    Polygon(
                        values = values,
                        unit = "$",
                        style = PolygonStyle(
                            fillColor = Color(0xFFADD8E6),
                            fillColorAlpha = 0.5f,
                            borderColor = Color(0xFF00008B),
                            borderColorAlpha = 0.5f,
                            borderStrokeWidth = 2f,
                            borderStrokeCap = StrokeCap.Butt,
                        )
                    ),
                    Polygon(
                        values = values2,
                        unit = "$",
                        style = PolygonStyle(
                            fillColor = Color(0xFF4682B4),
                            fillColorAlpha = 0.5f,
                            borderColor = Color(0xFF00008B),
                            borderColorAlpha = 0.5f,
                            borderStrokeWidth = 2f,
                            borderStrokeCap = StrokeCap.Butt
                        )
                    )
                )
            )
        }

    }
}