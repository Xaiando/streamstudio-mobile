package com.mobile.streamstudio

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.mobile.streamstudio.streaming.StreamingEngine
import com.mobile.streamstudio.ui.StudioApp
import com.mobile.streamstudio.ui.StudioViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: StudioViewModel

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = StudioViewModel(StreamingEngine(this))
        requestNotificationIfNeeded()

        setContent {
            StudioApp(viewModel)
        }
    }

    private fun requestNotificationIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val alreadyGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!alreadyGranted) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
