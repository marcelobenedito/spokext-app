package com.github.marcelobenedito.spokext

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.marcelobenedito.spokext.ui.SpokextApp
import com.github.marcelobenedito.spokext.ui.theme.SpokextTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpokextTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var canRecord by remember {
                        mutableStateOf(false)
                    }
                    val recordAudioLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            canRecord = isGranted
                        }
                    )
                    
                    LaunchedEffect(key1 = recordAudioLauncher) {
                        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                    SpokextApp()
                }
            }
        }
    }
}
