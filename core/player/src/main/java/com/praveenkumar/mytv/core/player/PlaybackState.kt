package com.praveenkumar.mytv.core.player

/**
 * Sealed interface representing the lifecycle and state of a video playback session.
 */
sealed interface PlaybackState {
    object Idle : PlaybackState
    object Buffering : PlaybackState
    data class Ready(val ttffMs: Long? = null) : PlaybackState
    object Ended : PlaybackState
    data class Error(val message: String, val errorCode: Int) : PlaybackState
}
