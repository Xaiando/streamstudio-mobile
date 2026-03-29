package com.mobile.streamstudio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobile.streamstudio.model.SourceType

@Composable
fun StudioApp(viewModel: StudioViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = viewModel::toggleOrientation) {
                Text("Orientation: ${state.profile.orientationMode}")
            }
            Button(onClick = viewModel::toggleStream) {
                Text(if (state.isStreaming) "Stop" else "Go Live")
            }
        }

        Text("Status: ${state.status}", color = Color.White)
        Text("Targets", color = Color.White, style = MaterialTheme.typography.titleMedium)
        state.targets.forEach { target ->
            Text("• ${target.platform}: ${target.streamUrl}", color = Color.LightGray)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.addOverlay(SourceType.IMAGE) }) { Text("+ PNG/GIF") }
            Button(onClick = { viewModel.addOverlay(SourceType.VIDEO) }) { Text("+ MP4/WEBM") }
            Button(onClick = { viewModel.addOverlay(SourceType.BROWSER_URL) }) { Text("+ URL") }
        }

        Text("Scene Layers", color = Color.White, style = MaterialTheme.typography.titleMedium)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.activeScene.sources, key = { it.id }) { source ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${source.label} (${source.type})")
                        Text("Opacity: ${(source.opacity * 100).toInt()}%")
                        Slider(
                            value = source.opacity,
                            onValueChange = { viewModel.updateOpacity(source.id, it) },
                            valueRange = 0f..1f
                        )
                        Text("Chroma key: ${if (source.chromaKeyEnabled) "On" else "Off"}")
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "OBS-lite mobile workflow: screen + internal audio + scene layers + multi-target output.",
                color = Color.Gray
            )
        }
    }
}
