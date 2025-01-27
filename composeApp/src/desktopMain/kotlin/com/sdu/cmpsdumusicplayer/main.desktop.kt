package com.sdu.cmpsdumusicplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sdu.cmpsdumusicplayer.decompose.MusicRoot

@Composable
fun CommonMainDesktop(rootComponent: MusicRoot) {
    Box(Modifier.background(color = Color(0xFF1A1E1F)).fillMaxSize()) {
        MainCommon(rootComponent, true)
    }
}