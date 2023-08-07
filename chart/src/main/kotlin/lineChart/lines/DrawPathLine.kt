import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lineChart.model.LineParameters


fun DrawScope.drawPathLineWrapper(
    lineParameter: LineParameters,
    spacing: Dp,
    xAxisSize: Int,
    strokePath: Path,
    function: (LineParameters, Int, Float, Float) -> Unit
) {
    val maxX = size.width + xAxisSize
    val maxY = size.height.toDp().toPx() - spacing.toPx()

    lineParameter.data.indices.forEach { index ->
        function(lineParameter, index, maxX, maxY)
    }

    drawPath(
        path = strokePath, color = lineParameter.lineColor, style = Stroke(
            width = 3.dp.toPx(), cap = StrokeCap.Round
        )
    )
}