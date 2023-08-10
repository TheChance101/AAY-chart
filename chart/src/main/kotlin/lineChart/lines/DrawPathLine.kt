import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import lineChart.model.LineParameters
import org.jetbrains.skia.impl.Log


fun DrawScope.drawPathLineWrapper(
    lineParameter: LineParameters,
    xAxisSize: Int,
    strokePath: Path,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingY:Dp,
    spacingX:Dp,
    function: (LineParameters, Int, Dp, Float) -> Unit,
) {
    val maxX = size.width.toDp() + xAxisSize*spacingX
    val maxY = size.height.toDp().toPx()
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