package com.sdu.cmpsdumusicplayer.playerview

import com.sdu.cmpsdumusicplayer.network.models.topfiftycharts.Item
import com.sdu.cmpsdumusicplayer.player.MediaPlayerController

data class PlayerViewState(
    val trackList: List<Item>,
    val mediaPlayerController: MediaPlayerController,
    val playingTrackId: String = ""
)

