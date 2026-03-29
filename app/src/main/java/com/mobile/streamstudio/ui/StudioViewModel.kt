package com.mobile.streamstudio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.streamstudio.model.OrientationMode
import com.mobile.streamstudio.model.Platform
import com.mobile.streamstudio.model.Scene
import com.mobile.streamstudio.model.SceneSource
import com.mobile.streamstudio.model.SourceType
import com.mobile.streamstudio.model.StreamProfile
import com.mobile.streamstudio.model.StreamTarget
import com.mobile.streamstudio.streaming.StreamingEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudioViewModel(
    private val streamingEngine: StreamingEngine
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        StudioUiState(
            scenes = listOf(
                Scene(
                    name = "Main Scene",
                    sources = listOf(
                        SceneSource(type = SourceType.SCREEN, label = "Display Capture"),
                        SceneSource(type = SourceType.DEVICE_AUDIO, label = "Device Audio"),
                        SceneSource(type = SourceType.MIC_AUDIO, label = "Mic Audio")
                    )
                )
            ),
            targets = defaultTargets()
        )
    )
    val uiState: StateFlow<StudioUiState> = _uiState.asStateFlow()

    fun toggleOrientation() {
        _uiState.update {
            val next = if (it.profile.orientationMode == OrientationMode.VERTICAL) {
                OrientationMode.HORIZONTAL
            } else {
                OrientationMode.VERTICAL
            }
            it.copy(profile = it.profile.copy(orientationMode = next))
        }
    }

    fun addOverlay(type: SourceType) {
        _uiState.update { state ->
            val source = SceneSource(type = type, label = "${type.name} Overlay", opacity = 0.9f)
            val scene = state.activeScene.copy(sources = state.activeScene.sources + source)
            state.copy(scenes = state.scenes.map { if (it.id == scene.id) scene else it })
        }
    }

    fun updateOpacity(sourceId: String, opacity: Float) {
        _uiState.update { state ->
            val newSources = state.activeScene.sources.map {
                if (it.id == sourceId) it.copy(opacity = opacity) else it
            }
            val newScene = state.activeScene.copy(sources = newSources)
            state.copy(scenes = state.scenes.map { if (it.id == newScene.id) newScene else it })
        }
    }

    fun toggleStream() {
        val state = _uiState.value
        if (state.isStreaming) {
            viewModelScope.launch {
                streamingEngine.stopStream()
                _uiState.update { it.copy(isStreaming = false, status = "Stream stopped") }
            }
        } else {
            viewModelScope.launch {
                val result = streamingEngine.startStream(state.profile, state.activeScene, state.targets)
                _uiState.update {
                    if (result.isSuccess) {
                        it.copy(isStreaming = true, status = "Live now")
                    } else {
                        it.copy(status = "Failed: ${result.exceptionOrNull()?.message}")
                    }
                }
            }
        }
    }

    private fun defaultTargets(): List<StreamTarget> = listOf(
        StreamTarget(platform = Platform.TWITCH, streamUrl = "rtmp://live.twitch.tv/app", streamKey = ""),
        StreamTarget(platform = Platform.KICK, streamUrl = "rtmp://fa723fc1b171.global-contribute.live-video.net:443/app", streamKey = ""),
        StreamTarget(platform = Platform.YOUTUBE, streamUrl = "rtmp://a.rtmp.youtube.com/live2", streamKey = ""),
        StreamTarget(platform = Platform.YOUTUBE_SHORTS, streamUrl = "rtmp://a.rtmp.youtube.com/live2", streamKey = ""),
        StreamTarget(platform = Platform.TIKTOK, streamUrl = "rtmp://push.tiktoklive.com/live", streamKey = "")
    )
}

data class StudioUiState(
    val scenes: List<Scene> = emptyList(),
    val selectedSceneId: String? = null,
    val targets: List<StreamTarget> = emptyList(),
    val profile: StreamProfile = StreamProfile(),
    val isStreaming: Boolean = false,
    val status: String = "Ready"
) {
    val activeScene: Scene
        get() = scenes.firstOrNull { it.id == selectedSceneId } ?: scenes.first()
}
