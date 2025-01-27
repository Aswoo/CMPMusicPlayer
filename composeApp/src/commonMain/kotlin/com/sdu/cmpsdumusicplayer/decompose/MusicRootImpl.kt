package com.sdu.cmpsdumusicplayer.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.sdu.cmpsdumusicplayer.network.SpotifyApi
import com.sdu.cmpsdumusicplayer.network.models.featuredplaylist.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer


class MusicRootImpl(
    componentContext: ComponentContext,
    private val dashboardMain: (ComponentContext, (DashboardMainComponent.Output) -> Unit) -> DashboardMainComponent,
    private val chartDetails: (
        ComponentContext, playlistId: String, playingTrackId: String, chatDetailsInput: SharedFlow<ChartDetailsComponent.Input>, (ChartDetailsComponent.Output) -> Unit
    ) -> ChartDetailsComponent,
) : MusicRoot, ComponentContext by componentContext {

    //to keep track of the playing track
    private var currentPlayingTrack = "-1"
    private val musicPlayerInput = MutableSharedFlow<PlayerComponent.Input>()
    private val chatDetailsInput = MutableSharedFlow<ChartDetailsComponent.Input>()

    private val navigation = StackNavigation<Configuration>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()

    private val stack = childStack(
        source = navigation,
        serializer = serializer(),
        initialConfiguration = Configuration.Dashboard,
        handleBackButton = true,
        childFactory = ::createChild
    )

    constructor(
        componentContext: ComponentContext,
        api: SpotifyApi,
    ) : this(
        componentContext = componentContext,
        dashboardMain = { childContext, output ->
            DashboardMainComponentImpl(
                componentContext = childContext, spotifyApi = api, output = output
            )
        },
        chartDetails = {
                       childContext, playlistId, playingTrackId, chartDetailsInput, output ->
            ChartDetailsComponentImpl(
                componentContext = childContext,
                spotifyApi = api,
                playlistId = playlistId,
                output = output,
                playingTrackId = playingTrackId,
                chatDetailsInput = chartDetailsInput
            )
        }
    ) {

    }

    private fun createChild(
        configuration: Configuration, componentContext: ComponentContext
    ): MusicRoot.Child = when (configuration) {
        Configuration.Dashboard -> MusicRoot.Child.Dashboard(
            dashboardMain(componentContext, ::dashboardOutput)
        )
        is Configuration.Details -> MusicRoot.Child.Details(
            chartDetails(
                componentContext,
                configuration.playlistId,
                currentPlayingTrack,
                chatDetailsInput,
                ::detailsOutput
            )
        )
    }
    private fun detailsOutput(output: ChartDetailsComponent.Output) {
        when (output) {
            is ChartDetailsComponent.Output.GoBack -> navigation.pop()
            is ChartDetailsComponent.Output.OnPlayAllSelected -> {
                dialogNavigation.activate(DialogConfig(output.playlist))
                CoroutineScope(Dispatchers.Default).launch {
                    musicPlayerInput.emit(PlayerComponent.Input.UpdateTracks(output.playlist))
                }
            }

            is ChartDetailsComponent.Output.OnTrackSelected -> {
                dialogNavigation.activate(DialogConfig(output.playlist, output.trackId))
                CoroutineScope(Dispatchers.Default).launch {
                    musicPlayerInput.emit(PlayerComponent.Input.PlayTrack(output.trackId, output.playlist))
                }
            }
        }
    }

    private fun dashboardOutput(output: DashboardMainComponent.Output) {
        when (output) {
            is DashboardMainComponent.Output.PlaylistSelected -> navigation.push(
                Configuration.Details(
                    output.playlistId, currentPlayingTrack
                )
            )
        }
    }

    private val player = childSlot(source = dialogNavigation,
        serializer = serializer(),
        initialConfiguration = { null },
        key = "PlayerView",
        handleBackButton = true,
        childFactory = { config, _ ->

        })



    override val childStack: Value<ChildStack<*, MusicRoot.Child>>
        get() = value()

    override val dialogOverlay: Value<ChildSlot<DialogConfig, Unit>>
        get() = player

    private fun value() = stack

    @Serializable
    private sealed class Configuration {
        @Serializable
        data object Dashboard : Configuration()

        @Serializable
        data class Details(
            val playlistId: String,
            val playingTrackId: String,
        ) : Configuration()
    }

    @Serializable
    data class DialogConfig(
        val playlist: List<Item>,
        val selectedTrack: String = ""
    )
}
