package com.praveenkumar.mytv.feature.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.praveenkumar.mytv.core.player.PlayerWrapper

/**
 * Full-screen video player composable backed by Jetpack Media3 ExoPlayer.
 *
 * @param videoUrl Direct media stream URL to play (HLS / MP4).
 * @param onBack Callback triggered when the user presses the Back button to exit playback.
 * @param modifier Composable modifier for layout configuration.
 */
@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    videoUrl: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Intercept system Back gesture / D-pad Back button
    BackHandler {
        onBack()
    }

    val context = LocalContext.current

    // Initialize ExoPlayer using core player wrapper
    val exoPlayer = remember(context, videoUrl) {
        PlayerWrapper.createAndPreparePlayer(context, videoUrl)
    }

    // Release player resources when leaving screen
    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Embed native Android PlayerView into Compose layout
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = true
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
