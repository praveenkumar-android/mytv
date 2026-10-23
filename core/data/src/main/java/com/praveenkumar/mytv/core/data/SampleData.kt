package com.praveenkumar.mytv.core.data

import com.praveenkumar.mytv.core.model.Category
import com.praveenkumar.mytv.core.model.VideoItem

/**
 * Sample dataset containing open-source video streams (HLS, MP4, Widevine DRM)
 * used for testing catalog rendering and ExoPlayer media playback.
 */
object SampleData {
    /**
     * List of sample videos with high quality streaming URLs, thumbnails, and DRM configurations.
     */
    val sampleVideos = listOf(
        VideoItem(
            id = "1",
            title = "Big Buck Bunny (MP4)",
            description = "A large and lovable rabbit deals with bullying forest creatures in this iconic open-source animated short.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
            bannerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
            category = "Animation",
            duration = "9:56"
        ),
        VideoItem(
            id = "2",
            title = "Tears of Steel (Widevine DRM / DASH)",
            description = "Protected Widevine CENC encrypted stream for testing DRM license acquisition and playback.",
            videoUrl = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg",
            bannerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg",
            category = "DRM Protected",
            duration = "12:14",
            drmLicenseUrl = "https://proxy.uat.widevine.com/proxy?provider=widevine_test"
        ),
        VideoItem(
            id = "3",
            title = "Elephant's Dream",
            description = "The story of two strange workers in a vast and bizarre machine world.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
            bannerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
            category = "Sci-Fi",
            duration = "10:53"
        ),
        VideoItem(
            id = "4",
            title = "For Bigger Blazes",
            description = "Experience the power of Google Chromecast streaming high quality video.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
            bannerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
            category = "Promos",
            duration = "0:15"
        ),
        VideoItem(
            id = "5",
            title = "Sintel",
            description = "A lonely young woman searches for her lost baby dragon in a mythical fantasy world.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
            bannerUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
            category = "Animation",
            duration = "14:48"
        )
    )

    /**
     * Grouping of sample videos into categories for horizontal content rows.
     */
    val sampleCategories = listOf(
        Category("c1", "Featured", sampleVideos),
        Category("c2", "DRM & Protection", sampleVideos.filter { it.drmLicenseUrl != null }),
        Category("c3", "Animation", sampleVideos.filter { it.category == "Animation" }),
        Category("c4", "Sci-Fi", sampleVideos.filter { it.category == "Sci-Fi" })
    )
}
