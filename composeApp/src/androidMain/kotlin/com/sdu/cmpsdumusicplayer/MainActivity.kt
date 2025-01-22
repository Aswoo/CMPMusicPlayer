package com.sdu.cmpsdumusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.sdu.cmpsdumusicplayer.decompose.MusicRootImpl
import com.sdu.cmpsdumusicplayer.network.SpotifyApiImpl
import musicapp.MainAndroid

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val api = SpotifyApiImpl()
        val root = MusicRootImpl(
            componentContext = defaultComponentContext(),
            api = api,
        )
        setContent {
            MainAndroid(root)
        }
    }
}
