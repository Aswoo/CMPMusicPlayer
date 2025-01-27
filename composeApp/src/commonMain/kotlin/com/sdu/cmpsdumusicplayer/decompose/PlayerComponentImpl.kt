package com.sdu.cmpsdumusicplayer.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.sdu.cmpsdumusicplayer.decompose.PlayerComponent
import com.sdu.cmpsdumusicplayer.network.models.topfiftycharts.Item
import com.sdu.cmpsdumusicplayer.player.MediaPlayerController
import kotlinx.coroutines.flow.SharedFlow
import musicapp.playerview.PlayerViewModel

class PlayerComponentImpl(
    componentContext: ComponentContext,
    private val mediaPlayerController: MediaPlayerController,
    private val trackList: List<Item>,
    private val selectedTrack: String,
    private val playerInputs: SharedFlow<PlayerComponent.Input>,
    val output: (PlayerComponent.Output) -> Unit
) : PlayerComponent, ComponentContext by componentContext {

    override val viewModel: PlayerViewModel
        get() = instanceKeeper.getOrCreate {
            PlayerViewModel(
                mediaPlayerController = mediaPlayerController,
                trackList = trackList,
                playerInputs = playerInputs,
                selectedTrack =selectedTrack
            )
        }

    override fun onOutPut(output: PlayerComponent.Output) {
        output(output)
    }
}

