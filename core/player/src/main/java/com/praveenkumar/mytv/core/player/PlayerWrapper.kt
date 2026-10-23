package com.praveenkumar.mytv.core.player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

/**
 * Helper utility to construct and configure Jetpack Media3 ExoPlayer instances.
 */
@OptIn(UnstableApi::class)
object PlayerWrapper {
    /**
     * Builds and prepares an [ExoPlayer] instance for the given video URL.
     *
     * @param context Application/Activity context.
     * @param videoUrl Direct media stream URL.
     * @return Configured and prepared [ExoPlayer].
     */
    fun createAndPreparePlayer(context: Context, videoUrl: String): ExoPlayer {
        return ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = true
        }
    }
}
