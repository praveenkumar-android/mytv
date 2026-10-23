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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.praveenkumar.mytv.core.data.SampleData
import com.praveenkumar.mytv.core.model.VideoItem
import com.praveenkumar.mytv.feature.catalog.mobile.MobileHomeScreen
import com.praveenkumar.mytv.feature.player.PlayerScreen
import com.praveenkumar.mytv.ui.theme.MyTvTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity entry point for the Mobile application.
 * Controls top-level navigation between the catalog screen ([MobileHomeScreen]) and video playback ([PlayerScreen]).
 */
@AndroidEntryPoint
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
                    // Holds the currently selected video for playback state, preserved across rotation and configuration changes
                    var selectedVideo by rememberSaveable { mutableStateOf<VideoItem?>(null) }

                    // Display PlayerScreen if a video is selected, otherwise render the mobile catalog
                    selectedVideo?.let { video ->
                        PlayerScreen(
                            video = video,
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
