import androidx.compose.foundation.layout.Column
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.sdu.cmpsdumusicplayer.MainCommon
import com.sdu.cmpsdumusicplayer.decompose.MusicRootImpl
import com.sdu.cmpsdumusicplayer.network.SpotifyApiImpl
import platform.UIKit.UIViewController

fun MainViewController(lifecycle: LifecycleRegistry): UIViewController = ComposeUIViewController {
    val rootComponent = MusicRootImpl(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        api = SpotifyApiImpl()
    )
    Column {
        MainCommon(rootComponent, false)
    }
}
