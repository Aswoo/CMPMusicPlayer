package com.sdu.cmpsdumusicplayer.decompose

import com.sdu.cmpsdumusicplayer.dashboard.DashboardViewModel


interface DashboardMainComponent {
    val viewModel: DashboardViewModel

    fun onOutPut(output: Output)

    sealed class Output {
        data class PlaylistSelected(val playlistId: String) : Output()
    }
}
