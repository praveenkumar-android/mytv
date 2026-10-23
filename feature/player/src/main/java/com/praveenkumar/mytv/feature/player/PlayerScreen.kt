package com.praveenkumar.mytv.feature.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.praveenkumar.mytv.core.model.VideoItem
import com.praveenkumar.mytv.core.player.PlaybackState
import com.praveenkumar.mytv.core.player.PlayerManager

/**
 * Full-screen video player composable backed by Jetpack Media3 ExoPlayer.
 *
 * Observes [PlaybackState] to display:
 * - Buffering progress indicator.
 * - Time-To-First-Frame (TTFF) metrics badge.
 * - Playback Error dialog with a Retry mechanism.
 *
 * @param video Video item containing streaming URL and optional DRM license server.
 * @param onBack Callback triggered when the user presses the Back button.
 * @param modifier Composable modifier for layout configuration.
 */
@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    video: VideoItem,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBack()
    }

    val context = LocalContext.current

    // Initialize PlayerManager which owns ExoPlayer instance, state callbacks, and TTFF metrics
    val playerManager = remember(context, video.id) {
        PlayerManager(
            context = context,
            videoUrl = video.videoUrl,
            drmLicenseUrl = video.drmLicenseUrl
        )
    }

    val playbackState by playerManager.playbackState.collectAsState()

    DisposableEffect(playerManager) {
        onDispose {
            playerManager.release()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Native Android PlayerView
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = playerManager.exoPlayer
                    useController = true
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Overlay 1: Buffering Spinner
        AnimatedVisibility(
            visible = playbackState is PlaybackState.Buffering,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Buffering video...",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Overlay 2: Time To First Frame (TTFF) Metric Badge
        (playbackState as? PlaybackState.Ready)?.ttffMs?.let { ttff ->
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "TTFF: ${ttff}ms",
                    color = Color.Green,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Overlay 3: Error Dialog with Retry
        (playbackState as? PlaybackState.Error)?.let { errorState ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.padding(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Playback Error",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${errorState.message} (Code: ${errorState.errorCode})",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { playerManager.retry() }) {
                            Text(text = "Retry Playback")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Backward compatible overload accepting direct video URL string.
 */
@Composable
fun PlayerScreen(
    videoUrl: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    PlayerScreen(
        video = VideoItem(
            id = videoUrl,
            title = "Video Playback",
            description = "",
            videoUrl = videoUrl,
            thumbnailUrl = "",
            category = "",
            duration = ""
        ),
        onBack = onBack,
        modifier = modifier
    )
}
