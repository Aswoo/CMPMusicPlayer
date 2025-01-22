package com.sdu.cmpsdumusicplayer.dashboard

import com.sdu.cmpsdumusicplayer.network.models.featuredplaylist.FeaturedPlayList
import com.sdu.cmpsdumusicplayer.network.models.newreleases.NewReleasedAlbums
import com.sdu.cmpsdumusicplayer.network.models.topfiftycharts.TopFiftyCharts

sealed interface DashboardViewState {
    data object Loading : DashboardViewState
    data class Success(
        val topFiftyCharts: TopFiftyCharts,
        val newReleasedAlbums: NewReleasedAlbums,
        val featuredPlayList: FeaturedPlayList
    ) : DashboardViewState

    data class Failure(val error: String) : DashboardViewState
}
