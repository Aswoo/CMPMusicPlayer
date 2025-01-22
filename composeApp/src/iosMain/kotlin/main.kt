import androidx.compose.ui.window.ComposeUIViewController
import com.sdu.cmpsdumusicplayer.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
