package com.praveenkumar.mytv.core.player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manages ExoPlayer lifecycle, state callbacks, Time-To-First-Frame (TTFF) measurement,
 * and Widevine DRM configuration.
 */
@OptIn(UnstableApi::class)
class PlayerManager(
    context: Context,
    videoUrl: String,
    drmLicenseUrl: String? = null
) {
    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.Idle)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private var prepareStartTimeMs: Long = 0L
    private var ttffMs: Long? = null

    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build().apply {
        val mediaItemBuilder = MediaItem.Builder().setUri(videoUrl)

        if (!drmLicenseUrl.isNullOrEmpty()) {
            mediaItemBuilder.setDrmConfiguration(
                MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                    .setLicenseUri(drmLicenseUrl)
                    .build()
            )
        }

        setMediaItem(mediaItemBuilder.build())

        addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_IDLE -> _playbackState.value = PlaybackState.Idle
                    Player.STATE_BUFFERING -> _playbackState.value = PlaybackState.Buffering
                    Player.STATE_READY -> _playbackState.value = PlaybackState.Ready(ttffMs)
                    Player.STATE_ENDED -> _playbackState.value = PlaybackState.Ended
                }
            }

            override fun onRenderedFirstFrame() {
                if (prepareStartTimeMs > 0) {
                    ttffMs = System.currentTimeMillis() - prepareStartTimeMs
                    _playbackState.value = PlaybackState.Ready(ttffMs)
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                _playbackState.value = PlaybackState.Error(
                    message = error.localizedMessage ?: "Playback error encountered",
                    errorCode = error.errorCode
                )
            }
        })

        prepareStartTimeMs = System.currentTimeMillis()
        prepare()
        playWhenReady = true
    }

    /**
     * Retries playback preparation after an error.
     */
    fun retry() {
        prepareStartTimeMs = System.currentTimeMillis()
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    /**
     * Releases player resources.
     */
    fun release() {
        exoPlayer.release()
    }
}

/**
 * Backward-compatible helper utility.
 */
@OptIn(UnstableApi::class)
object PlayerWrapper {
    fun createAndPreparePlayer(context: Context, videoUrl: String): ExoPlayer {
        return PlayerManager(context, videoUrl).exoPlayer
    }
}
