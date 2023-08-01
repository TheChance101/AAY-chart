import androidx.compose.ui.graphics.Color
import model.*

object ChartDefault {
    private val lineParameters:LineParameters= LineParameters(
        "revenue",
        emptyList(),
        Color.Blue,
        LineType.QUADRATIC_LINE,
        LineShadow.SHADOW
        )

    val chart:Chart = Chart(
        listOf(lineParameters),
        BackGroundGrid.SHOW,
        Color.White,
        "month",
        "money"
    )
}