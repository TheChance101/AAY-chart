import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.aay.common.chart.TestAxesDrawing


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        TestAxesDrawing()
    }
}
