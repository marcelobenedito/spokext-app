package com.github.marcelobenedito.spokext.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.marcelobenedito.spokext.data.VoiceToTextListener
import com.github.marcelobenedito.spokext.data.VoiceToTextListenerState
import com.github.marcelobenedito.spokext.ui.theme.Black

@Composable
fun NoteEditorScreen(
    state: VoiceToTextListenerState,
    recordListener: VoiceToTextListener,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp)
    ) {
        BasicTextField(
            value = state.spokenText,
            onValueChange = { recordListener.onTextChange(it) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Black),
            decorationBox = { innerTextField ->
                if (state.spokenText.isBlank()) {
                    Text(
                        text = "Type your notes here...",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                    )
                }
                innerTextField()
            },
            modifier = Modifier.fillMaxSize()
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = Black
                )
            }
            IconButton(onClick = {
                if (state.isSpeaking) {
                    recordListener.stopListening()
                } else {
                    recordListener.startListening()
                }
            }) {
                Icon(
                    imageVector = if (state.isSpeaking) Icons.Rounded.Stop else Icons.Rounded.Mic,
                    contentDescription = null,
                    tint = if (state.isSpeaking) Color.Red else Black
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Save,
                    contentDescription = null,
                    tint = Black
                )
            }
        }
    }
}
