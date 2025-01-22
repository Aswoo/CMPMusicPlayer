package com.sdu.cmpsdumusicplayer.network

import com.sdu.cmpsdumusicplayer.network.models.featuredplaylist.FeaturedPlayList
import com.sdu.cmpsdumusicplayer.network.models.newreleases.NewReleasedAlbums
import com.sdu.cmpsdumusicplayer.network.models.topfiftycharts.TopFiftyCharts

interface SpotifyApi {
    suspend fun getTopFiftyChart(): TopFiftyCharts
    suspend fun getNewReleases(): NewReleasedAlbums
    suspend fun getFeaturedPlaylist(): FeaturedPlayList
    suspend fun getPlayList(playlistId: String): TopFiftyCharts
}