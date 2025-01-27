package com.sdu.cmpsdumusicplayer.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.sdu.cmpsdumusicplayer.chartdetails.ChartDetailsViewModel
import com.sdu.cmpsdumusicplayer.network.SpotifyApi
import kotlinx.coroutines.flow.SharedFlow

class ChartDetailsComponentImpl(
    componentContext: ComponentContext,
    val spotifyApi: SpotifyApi,
    val playlistId: String,
    val playingTrackId: String,
    val chatDetailsInput: SharedFlow<ChartDetailsComponent.Input>,
    val output: (ChartDetailsComponent.Output) -> Unit,
) : ChartDetailsComponent, ComponentContext by componentContext {
    override val viewModel: ChartDetailsViewModel
        get() = instanceKeeper.getOrCreate {
            ChartDetailsViewModel(
                spotifyApi,
                playlistId,
                playingTrackId,
                chatDetailsInput
            )
        }

    override fun onOutPut(output: ChartDetailsComponent.Output) {
        output(output)
    }
}