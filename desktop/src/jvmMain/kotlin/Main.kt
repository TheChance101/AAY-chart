import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.aay.common.App
import com.aay.common.AppPreview


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
       AppPreview()
    }
}
