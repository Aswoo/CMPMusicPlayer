package com.sdu.cmpsdumusicplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.sdu.cmpsdumusicplayer.decompose.MusicRoot
import com.sdu.cmpsdumusicplayer.decompose.MusicRootImpl
import com.sdu.cmpsdumusicplayer.network.SpotifyApiImpl
import com.sdu.cmpsdumusicplayer.player.MediaPlayerController
import com.sdu.cmpsdumusicplayer.player.PlatformContext
import java.awt.Dimension


fun main() = application {
    Window(
        title = "Multiplatform App",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        val lifecycle = LifecycleRegistry()
        val api = remember { SpotifyApiImpl() }
        val root = remember {
            MusicRootImpl(
                componentContext = DefaultComponentContext(lifecycle),
                api = api,
                mediaPlayerController = MediaPlayerController(PlatformContext()) // Desktop용 Context
            )
        }
        CommonMainDesktop(root)
    }
}
@Composable
fun CommonMainDesktop(rootComponent: MusicRoot) {
    Box(Modifier.background(color = Color(0xFF1A1E1F)).fillMaxSize()) {
        MainCommon(rootComponent, true)
    }
}