import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.aay.compose.AppPreview


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        AppPreview()
    }
}
