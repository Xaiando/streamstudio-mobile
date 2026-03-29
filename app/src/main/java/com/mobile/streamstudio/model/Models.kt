package com.mobile.streamstudio.model

import java.util.UUID

enum class Platform {
    TWITCH,
    KICK,
    YOUTUBE,
    YOUTUBE_SHORTS,
    TIKTOK
}

enum class OrientationMode {
    HORIZONTAL,
    VERTICAL
}

enum class SourceType {
    SCREEN,
    DEVICE_AUDIO,
    MIC_AUDIO,
    IMAGE,
    VIDEO,
    BROWSER_URL
}

data class StreamTarget(
    val id: String = UUID.randomUUID().toString(),
    val platform: Platform,
    val streamUrl: String,
    val streamKey: String,
    val enabled: Boolean = true
)

data class SceneSource(
    val id: String = UUID.randomUUID().toString(),
    val type: SourceType,
    val label: String,
    val x: Float = 0f,
    val y: Float = 0f,
    val scale: Float = 1f,
    val opacity: Float = 1f,
    val chromaKeyEnabled: Boolean = false,
    val chromaKeyColor: Long = 0xFF00FF00,
    val alphaEnabled: Boolean = true,
    val contentPath: String? = null,
    val embedUrl: String? = null,
    val muted: Boolean = false
)

data class Scene(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val sources: List<SceneSource>
)

data class StreamProfile(
    val orientationMode: OrientationMode = OrientationMode.VERTICAL,
    val bitrateKbps: Int = 4500,
    val fps: Int = 30,
    val audioBitrateKbps: Int = 160,
    val useDeviceAudioCapture: Boolean = true,
    val useMicAudioCapture: Boolean = true
)
