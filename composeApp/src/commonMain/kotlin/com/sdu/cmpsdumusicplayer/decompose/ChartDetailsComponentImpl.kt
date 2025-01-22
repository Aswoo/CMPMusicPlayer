package com.sdu.cmpsdumusicplayer.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.sdu.cmpsdumusicplayer.chartdetails.ChartDetailsViewModel
import com.sdu.cmpsdumusicplayer.network.SpotifyApi
import kotlinx.coroutines.flow.SharedFlow

class ChartDetailsComponentImpl(
    componentContext: ComponentContext,
    val spotifyApi: SpotifyApi
) : ChartDetailsComponent, ComponentContext by componentContext {
    override val viewModel: ChartDetailsViewModel
        get() = instanceKeeper.getOrCreate {
            ChartDetailsViewModel(
                spotifyApi
            )
        }

    override fun onOutPut(output: ChartDetailsComponent.Output) {

    }
}