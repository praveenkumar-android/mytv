package com.praveenkumar.mytv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.praveenkumar.mytv.data.SampleData
import com.praveenkumar.mytv.data.VideoItem
import com.praveenkumar.mytv.ui.home.MobileHomeScreen
import com.praveenkumar.mytv.ui.player.PlayerScreen
import com.praveenkumar.mytv.ui.theme.MyTvTheme

/**
 * Main Activity entry point for the Mobile application.
 * Controls top-level navigation between the catalog screen ([MobileHomeScreen]) and video playback ([PlayerScreen]).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTvTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Holds the currently selected video for playback state
                    var selectedVideo by remember { mutableStateOf<VideoItem?>(null) }

                    // Display PlayerScreen if a video is selected, otherwise render the mobile catalog
                    selectedVideo?.let { video ->
                        PlayerScreen(
                            videoUrl = video.videoUrl,
                            onBack = { selectedVideo = null }
                        )
                    } ?: MobileHomeScreen(
                        categories = SampleData.sampleCategories,
                        onVideoSelected = { video ->
                            selectedVideo = video
                        }
                    )
                }
            }
        }
    }
}
