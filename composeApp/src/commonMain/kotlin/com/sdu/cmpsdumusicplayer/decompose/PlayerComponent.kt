package com.sdu.cmpsdumusicplayer.decompose

import com.sdu.cmpsdumusicplayer.network.models.featuredplaylist.Item

interface PlayerComponent {
//    val viewModel: PlayerViewModel

    fun onOutPut(output: Output)

    sealed class Output {
        data object OnPause : Output()
        data object OnPlay : Output()
        data class OnTrackUpdated(val trackId: String) : Output()
    }

    sealed interface Input {
        data class PlayTrack(val trackId: String, val tracksList: List<Item>) : Input
        data class UpdateTracks(val tracksList: List<Item>) : Input
    }

}
