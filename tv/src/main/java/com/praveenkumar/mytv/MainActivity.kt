package com.praveenkumar.mytv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.praveenkumar.mytv.core.data.SampleData
import com.praveenkumar.mytv.core.model.VideoItem
import com.praveenkumar.mytv.feature.catalog.tv.TvHomeScreen
import com.praveenkumar.mytv.feature.player.PlayerScreen
import com.praveenkumar.mytv.ui.theme.MyTvTheme

/**
 * Main Activity entry point for the Android TV application.
 * Controls top-level navigation between the catalog screen ([TvHomeScreen]) and video playback ([PlayerScreen]).
 */
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTvTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    // Holds the currently selected video for playback state
                    var selectedVideo by remember { mutableStateOf<VideoItem?>(null) }

                    // Display PlayerScreen if a video is selected, otherwise render the TV catalog
                    selectedVideo?.let { video ->
                        PlayerScreen(
                            videoUrl = video.videoUrl,
                            onBack = { selectedVideo = null }
                        )
                    } ?: TvHomeScreen(
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
