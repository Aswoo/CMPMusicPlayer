package com.sdu.cmpsdumusicplayer.network

import com.sdu.cmpsdumusicplayer.TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import com.sdu.cmpsdumusicplayer.network.models.featuredplaylist.FeaturedPlayList
import com.sdu.cmpsdumusicplayer.network.models.newreleases.NewReleasedAlbums
import com.sdu.cmpsdumusicplayer.network.models.topfiftycharts.TopFiftyCharts
import com.sdu.cmpsdumusicplayer.sampledata.featurePlaylistResponse
import com.sdu.cmpsdumusicplayer.sampledata.newReleases
import com.sdu.cmpsdumusicplayer.sampledata.topFiftyChartsResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SpotifyApiImpl : SpotifyApi {

    override suspend fun getTopFiftyChart(): TopFiftyCharts {
        if (TOKEN.isEmpty()) {
            return Json.decodeFromString<TopFiftyCharts>(topFiftyChartsResponse)
        }
        return client.get {
            headers {
                sptifyEndPoint("v1/playlists/37i9dQZEVXbMDoHDwVN2tF")
            }
        }.body()
    }

    override suspend fun getNewReleases(): NewReleasedAlbums {
        if (TOKEN.isEmpty()) {
            return Json.decodeFromString<NewReleasedAlbums>(newReleases)
        }
        return client.get {
            headers {
                sptifyEndPoint("v1/browse/new-releases")
            }
        }.body()
    }

    override suspend fun getFeaturedPlaylist(): FeaturedPlayList {
        if (TOKEN.isEmpty()) {
            return Json.decodeFromString<FeaturedPlayList>(featurePlaylistResponse)
        }
        return client.get {
            headers {
                sptifyEndPoint("v1/browse/featured-playlists")
            }
        }.body()
    }

    override suspend fun getPlayList(playlistId: String): TopFiftyCharts {
        if (TOKEN.isEmpty()) {
            return Json.decodeFromString<TopFiftyCharts>(topFiftyChartsResponse)
        }
        return client.get {
            headers {
                sptifyEndPoint("v1/playlists/$playlistId")
            }
        }.body()
    }

    private val client = HttpClient {
        expectSuccess = true
        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                },
                contentType = ContentType.Application.Json
            )
        }
    }

    private fun HttpRequestBuilder.sptifyEndPoint(path: String) {
        url {
            takeFrom("https://api.spotify.com/v1/")
            encodedPath = path
            headers {
                append(
                    HttpHeaders.Authorization, TOKEN
                )
            }
        }
    }
}