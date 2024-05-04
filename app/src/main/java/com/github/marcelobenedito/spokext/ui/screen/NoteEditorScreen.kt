package com.github.marcelobenedito.spokext.ui.screen

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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.marcelobenedito.spokext.R
import com.github.marcelobenedito.spokext.data.VoiceToTextListenerState
import com.github.marcelobenedito.spokext.ui.theme.Black
import com.github.marcelobenedito.spokext.ui.viewmodel.NoteEditorViewModel

@Composable
fun NoteEditorScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteEditorViewModel = viewModel(factory = NoteEditorViewModel.Factory)
) {
    val state: VoiceToTextListenerState by viewModel.state.collectAsState()
    val focusRequester = FocusRequester()
    SideEffect {
        focusRequester.requestFocus()
    }

    Box(
        modifier = modifier.padding(16.dp)
    ) {
        BasicTextField(
            value = state.spokenText,
            onValueChange = viewModel::onTextChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Black),
            decorationBox = { innerTextField ->
                if (state.spokenText.isBlank()) {
                    Text(
                        text = stringResource(R.string.type_notes_placeholder),
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                    )
                }
                innerTextField()
            },
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(onClick = viewModel::discardNote) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                if (state.isSpeaking) viewModel.stopListening() else viewModel.startListening()
            }) {
                Icon(
                    imageVector = if (state.isSpeaking) Icons.Rounded.Stop else Icons.Rounded.Mic,
                    contentDescription = null,
                    tint = if (state.isSpeaking) Color.Red else LocalContentColor.current
                )
            }
            IconButton(onClick = viewModel::openTitleInputDialog) {
                Icon(
                    imageVector = Icons.Rounded.Save,
                    contentDescription = null
                )
            }
        }
    }
}
