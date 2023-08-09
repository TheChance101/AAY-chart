import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lineChart.model.LineParameters


fun DrawScope.drawPathLineWrapper(
    lineParameter: LineParameters,
    xAxisSize: Int,
    strokePath: Path,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    function: (LineParameters, Int, Float, Float) -> Unit,
) {
    val spacingX = (size.width/5.dp.toPx()).dp
    val spacingY = (size.height/5.dp.toPx()).dp
    val maxX = size.width + xAxisSize
    val maxY = size.height.toDp().toPx() - spacingY.toPx()

    lineParameter.data.indices.forEach { index ->
        function(lineParameter, index, maxX, maxY)
    }
    clipRect(right = size.width * animatedProgress.value) {
        drawPath(
            path = strokePath, color = lineParameter.lineColor, style = Stroke(
                width = 3.dp.toPx(), cap = StrokeCap.Round
            )
        )
    }
}