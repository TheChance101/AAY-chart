package chart
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.*

@Composable
fun TestAxesDrawing(){

     val lineParameters: LineParameters = LineParameters(
        "revenue",
         listOf(
             Pair("Jan",10000.0),
             Pair("Feb",20000.0),
             Pair("Mar",40000.0),
             Pair("Apr",50000.0),
             Pair("May",20000.0),
             Pair("Aug",70000.0),
             Pair("Sep",50000.0),
             Pair("Oct",90000.0),
         ),
        Color.Blue,
        LineType.QUADRATIC_LINE,
        LineShadow.SHADOW
    )
    val lineParameters2: LineParameters = LineParameters(
        "revenue",
        listOf(
            Pair("Jan",50000.0),
            Pair("Feb",10000.0),
            Pair("Mar",40000.0),
            Pair("Apr",30000.0),
            Pair("May",40000.0),
            Pair("Aug",50000.0),
            Pair("Sep",50000.0),
            Pair("Oct",90000.0),
        ),
        Color.Blue,
        LineType.QUADRATIC_LINE,
        LineShadow.BLANK
    )

    val chart: Chart = Chart(
        listOf(lineParameters,lineParameters2),
        BackGroundGrid.SHOW,
        Color.White,
        "month",
        "money"
    )
    val revenueData = listOf(
        Pair("Jan",10000),
        Pair("Feb",20000),
        Pair("Mar",40000),
        Pair("Apr",50000),
        Pair("May",20000),
        Pair("Aug",70000),
        Pair("Sep",50000),
        Pair("Oct",90000),
        )
    Column (modifier = Modifier.fillMaxSize()) {
        AxesDrawing(
            modifier = Modifier.size(500.dp).align(Alignment.CenterHorizontally).padding(top = 24.dp),
            chart.lines
        )
    }
}