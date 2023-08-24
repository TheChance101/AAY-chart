package com.aay.compose.lineChart.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.utils.formatToThousandsMillionsBillions

@OptIn(ExperimentalTextApi::class)
fun DrawScope.chartRectangleWithText(
    x: Dp , y: Double , color: Color , textMeasurer: TextMeasurer , infoText : Double
){
    val rectSize = Size(50.dp.toPx(), 30.dp.toPx())
    val rectTopLeft = Offset(
        x.toPx() - rectSize.width / 2,
        y.toFloat() - rectSize.height * 1.5.toFloat()
    )
    val rectBounds = Rect(rectTopLeft, rectSize)
    val text = "Value:${infoText.toLong().formatToThousandsMillionsBillions()}"

    val textStyle = TextStyle(fontSize = 8.sp, color = Color.Black)

    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(text),
        style = textStyle
    )

    val textOffset = Offset(
        rectTopLeft.x + rectSize.width / 2 - textLayoutResult.size.width / 2,
        rectTopLeft.y + rectSize.height / 4 + textLayoutResult.size.height / 2 // Adjust this line
    )

    drawRoundRect(
        color = color,
        topLeft = rectBounds.topLeft,
        size = rectBounds.size,
        cornerRadius = CornerRadius(16.dp.toPx()),
        style = Stroke(width = 1.dp.toPx())
    )

    drawContext.canvas.nativeCanvas.apply {
        drawText(
            textMeasurer = textMeasurer,
            text = text,
            style = textStyle,
            topLeft = textOffset
        )
    }



}