package com.sdu.cmpsdumusicplayer.decompose

import com.sdu.cmpsdumusicplayer.chartdetails.ChartDetailsViewModel
import com.sdu.cmpsdumusicplayer.network.models.topfiftycharts.Item
import kotlinx.serialization.Serializable



interface ChartDetailsComponent {
    val viewModel: ChartDetailsViewModel
    fun onOutPut(output: Output)
    sealed class Output {
        data object GoBack : Output()
        data class OnPlayAllSelected(val playlist: List<Item>) : Output()
        data class OnTrackSelected(val trackId: String, val playlist: List<Item>) : Output()
    }

    @Serializable
    sealed interface Input {

        @Serializable
        data class TrackUpdated(val trackId: String) : Input
    }
}
