package com.praveenkumar.mytv.core.model

/**
 * Represents an individual video item in the media catalog.
 *
 * @property id Unique identifier for the video.
 * @property title The display title of the video.
 * @property description Detailed summary of the video content.
 * @property videoUrl Direct streaming URL for the video (MP4/HLS).
 * @property thumbnailUrl Poster image URL used for video cards.
 * @property bannerUrl High-resolution background/hero banner image URL.
 * @property category Genre or category tag for filtering.
 * @property duration Formatted duration string (e.g., "9:56").
 */
data class VideoItem(
    val id: String,
    val title: String,
    val description: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val bannerUrl: String = thumbnailUrl,
    val category: String,
    val duration: String
)

/**
 * Represents a category grouping multiple [VideoItem]s for display in horizontal rows.
 *
 * @property id Unique identifier for the category row.
 * @property name Display name of the category (e.g., "Featured", "Animation").
 * @property videos List of video items belonging to this category.
 */
data class Category(
    val id: String,
    val name: String,
    val videos: List<VideoItem>
)
