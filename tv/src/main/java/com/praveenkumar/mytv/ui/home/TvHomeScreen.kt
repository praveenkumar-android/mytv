package com.praveenkumar.mytv.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.praveenkumar.mytv.data.Category
import com.praveenkumar.mytv.data.VideoItem

/**
 * Main Home Screen UI for Android TV built with Jetpack TV Material3.
 *
 * Features:
 * - Dynamic hero banner displaying details and artwork for the currently focused video item.
 * - Categorized horizontal rows ([CategoryRow]) with video cards ([VideoCard]).
 * - TV D-pad focus handling with interactive card scaling animations.
 *
 * @param categories List of video categories to display.
 * @param onVideoSelected Callback invoked when a video card is clicked/selected with the D-pad Center button.
 * @param modifier Composable modifier for layout styling.
 */
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvHomeScreen(
    categories: List<Category>,
    onVideoSelected: (VideoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    // State tracking the currently focused video to dynamically update the hero section
    var featuredVideo by remember {
        mutableStateOf(categories.firstOrNull()?.videos?.firstOrNull())
    }

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Dynamic Background Artwork corresponding to the focused video item
            featuredVideo?.let { video ->
                AsyncImage(
                    model = video.bannerUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                )

                // Dark gradient overlay to ensure text contrast over background artwork
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                )
            }

            // Foreground Catalog Scroll View
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // Hero Banner Section (Title, Category, Duration, Description)
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 48.dp, top = 48.dp, end = 48.dp, bottom = 24.dp)
                    ) {
                        Text(
                            text = "MyTv",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        featuredVideo?.let { video ->
                            Text(
                                text = video.title,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${video.category} • ${video.duration}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = video.description,
                                fontSize = 14.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth(0.6f),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // Category Rows
                items(categories) { category ->
                    CategoryRow(
                        category = category,
                        onVideoFocused = { video ->
                            featuredVideo = video
                        },
                        onVideoClick = onVideoSelected
                    )
                }
            }
        }
    }
}

/**
 * A horizontal row displaying a single category title and its list of video cards.
 */
@Composable
private fun CategoryRow(
    category: Category,
    onVideoFocused: (VideoItem) -> Unit,
    onVideoClick: (VideoItem) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Text(
            text = category.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 48.dp, bottom = 12.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 48.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(category.videos) { video ->
                VideoCard(
                    video = video,
                    onFocused = { onVideoFocused(video) },
                    onClick = { onVideoClick(video) }
                )
            }
        }
    }
}

/**
 * Interactive TV video card component.
 * Listens for D-pad focus state changes to trigger scale animation and update the hero banner.
 */
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun VideoCard(
    video: VideoItem,
    onFocused: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    onFocused()
                }
            },
        scale = CardDefaults.scale(focusedScale = 1.08f)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = video.thumbnailUrl,
                contentDescription = video.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Scrim overlay for text legibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            Text(
                text = video.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}
