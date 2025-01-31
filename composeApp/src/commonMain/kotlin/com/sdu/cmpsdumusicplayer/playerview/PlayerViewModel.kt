package musicapp.playerview

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.sdu.cmpsdumusicplayer.SEEK_TO_SECONDS
import com.sdu.cmpsdumusicplayer.decompose.PlayerComponent
import com.sdu.cmpsdumusicplayer.network.models.topfiftycharts.Item
import com.sdu.cmpsdumusicplayer.playerview.PlayerViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.sdu.cmpsdumusicplayer.player.MediaPlayerController


class PlayerViewModel(
    private val mediaPlayerController: MediaPlayerController,
    trackList: List<Item>,
    selectedTrack: String,
    playerInputs: SharedFlow<PlayerComponent.Input>
) : InstanceKeeper.Instance {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + coroutineExceptionHandler + job)

    val playerViewState = MutableStateFlow(
        PlayerViewState(
            trackList = trackList,
            mediaPlayerController = mediaPlayerController,
            playingTrackId = selectedTrack
        )
    )

    init {
        viewModelScope.launch {
            playerInputs.collectLatest {
                when (it) {
                    is PlayerComponent.Input.PlayTrack -> {
                        playerViewState.value =
                            playerViewState.value.copy(playingTrackId = it.trackId, trackList = it.tracksList)
                    }

                    is PlayerComponent.Input.UpdateTracks ->
                        playerViewState.value = playerViewState.value.copy(trackList = it.tracksList)
                }
            }
        }
    }

    fun rewind5Seconds() {
        mediaPlayerController.getCurrentPosition()?.let {
            (it - SEEK_TO_SECONDS).coerceAtLeast(0).let(mediaPlayerController::seekTo)
        }
    }

    fun forward5Seconds() {
        mediaPlayerController.getCurrentPosition()?.let { currentPosition ->
            mediaPlayerController.getDuration()?.let { duration ->
                (currentPosition + SEEK_TO_SECONDS).coerceAtMost(duration)
                    .let(mediaPlayerController::seekTo)
            }
        }
    }

    override fun onDestroy() {
        viewModelScope.cancel()
    }
}