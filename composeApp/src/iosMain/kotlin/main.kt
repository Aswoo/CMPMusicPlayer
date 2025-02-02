import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.sdu.cmpsdumusicplayer.MainCommon
import com.sdu.cmpsdumusicplayer.decompose.MusicRootImpl
import com.sdu.cmpsdumusicplayer.network.SpotifyApiImpl
import com.sdu.cmpsdumusicplayer.player.MediaPlayerController
import com.sdu.cmpsdumusicplayer.player.PlatformContext
import platform.UIKit.UIViewController

fun MainViewController(lifecycle: LifecycleRegistry): UIViewController = ComposeUIViewController {
    val rootComponent = MusicRootImpl(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        api = SpotifyApiImpl(),
        mediaPlayerController = MediaPlayerController(PlatformContext())
    )
    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(40.dp).background(color = Color(0xFF1A1E1F))
        ){
            MainCommon(rootComponent, false)
        }
    }
}
