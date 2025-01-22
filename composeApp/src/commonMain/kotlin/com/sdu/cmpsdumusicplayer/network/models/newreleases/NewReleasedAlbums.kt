package com.sdu.cmpsdumusicplayer.network.models.newreleases


import com.sdu.cmpsdumusicplayer.network.models.newreleases.Albums
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewReleasedAlbums(
    @SerialName("albums")
    val albums: Albums?
)