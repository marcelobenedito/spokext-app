package com.github.marcelobenedito.spokext.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.marcelobenedito.spokext.data.VoiceToTextListener
import com.github.marcelobenedito.spokext.data.VoiceToTextListenerState

@Composable
fun SpokextApp(listener: VoiceToTextListener, state: VoiceToTextListenerState, modifier: Modifier = Modifier) {
    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    if (state.isSpeaking) {
//                        listener.stopListening()
//                    } else {
//                        listener.startListening()
//                    }
//                }
//            ) {
//                AnimatedContent(targetState = state.isSpeaking) { isSpeaking ->
//                    if (isSpeaking) {
//                        Icon(imageVector = Icons.Rounded.Stop, contentDescription = null)
//                    } else {
//                        Icon(imageVector = Icons.Rounded.Mic, contentDescription = null)
//                    }
//                }
//            }
//        }
    ) { padding ->
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(20.dp)
//        ) {
//            AnimatedContent(targetState = state.isSpeaking) { isSpeaking ->
//                if (isSpeaking) {
//                    Text(text = "Speaking...")
//                }
//            }
//            Spacer(modifier = Modifier.height(24.dp))
//            Text(text = state.spokenText.ifEmpty { "Click on mic to record audio" })
//        }
        NoteEditorScreen(
            state = state,
            recordListener = listener,
            modifier = modifier.padding(padding)
        )
    }
}
