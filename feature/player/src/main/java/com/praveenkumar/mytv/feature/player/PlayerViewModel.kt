package com.praveenkumar.mytv.feature.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.praveenkumar.mytv.core.model.VideoItem
import com.praveenkumar.mytv.core.player.PlaybackState
import com.praveenkumar.mytv.core.player.PlayerManager
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel managing video player instance and playback state across configuration changes (e.g. rotation).
 * Uses ApplicationContext to avoid Activity context leaks.
 */
class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private var playerManager: PlayerManager? = null

    val playbackState: StateFlow<PlaybackState>?
        get() = playerManager?.playbackState

    val exoPlayer
        get() = playerManager?.exoPlayer

    /**
     * Initializes the player with ApplicationContext if not already instantiated.
     */
    fun initializePlayer(video: VideoItem) {
        if (playerManager == null) {
            playerManager = PlayerManager(
                context = getApplication(),
                videoUrl = video.videoUrl,
                drmLicenseUrl = video.drmLicenseUrl
            )
        }
    }

    /**
     * Retries playback after an error.
     */
    fun retry() {
        playerManager?.retry()
    }

    /**
     * Cleans up player resources when ViewModel is cleared (screen popped off backstack).
     */
    override fun onCleared() {
        super.onCleared()
        playerManager?.release()
        playerManager = null
    }
}
