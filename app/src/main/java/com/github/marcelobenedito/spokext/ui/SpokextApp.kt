package com.github.marcelobenedito.spokext.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.marcelobenedito.spokext.data.SpokextVoiceToTextListener
import com.github.marcelobenedito.spokext.data.VoiceToTextListenerState
import com.github.marcelobenedito.spokext.ui.screen.NoteEditorScreen

@Composable
fun SpokextApp(modifier: Modifier = Modifier) {
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
        NoteEditorScreen(modifier = modifier.padding(padding))
    }
}
