package com.github.marcelobenedito.spokext.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.marcelobenedito.spokext.R
import com.github.marcelobenedito.spokext.ui.theme.Black

@Composable
fun NoteEditorScreen(
    spokenText: String,
    isSpeaking: Boolean,
    isDisplayingTitleDialog: Boolean,
    onTextChange: (String) -> Unit,
    discardNote: () -> Unit,
    startListening: () -> Unit,
    stopListening: () -> Unit,
    openTitleInputDialog: () -> Unit,
    closeTitleInputDialog: () -> Unit,
    saveNote: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = FocusRequester()
    SideEffect {
        focusRequester.requestFocus()
    }

    Box(
        modifier = modifier.padding(16.dp)
    ) {
        if (isDisplayingTitleDialog)
            TitleDialog(
                saveNote = saveNote,
                closeTitleInputDialog = closeTitleInputDialog
            )

        BasicTextField(
            value = spokenText,
            onValueChange = onTextChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Black),
            decorationBox = { innerTextField ->
                if (spokenText.isBlank()) {
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
            IconButton(
                onClick = {
                    stopListening()
                    discardNote()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                if (isSpeaking) stopListening() else startListening()
            }) {
                Icon(
                    imageVector = if (isSpeaking) Icons.Rounded.Stop else Icons.Rounded.Mic,
                    contentDescription = null,
                    tint = if (isSpeaking) Color.Red else LocalContentColor.current
                )
            }
            IconButton(
                onClick = {
                    stopListening()
                    openTitleInputDialog()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Save,
                    contentDescription = null
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleDialog(
    saveNote: (String) -> Unit,
    closeTitleInputDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {}) {
        Card(
            onClick = {},
            modifier = modifier
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Please, type a title for your note:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    label = { Text(text = "Title") },
                    singleLine = true,
                    onValueChange = { title = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = closeTitleInputDialog) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { saveNote(title) }) {
                        Text(text = "Continue")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NoteEditorScreenPreview() {
    MaterialTheme {
        Surface {
            NoteEditorScreen(
                spokenText = "",
                isSpeaking = false,
                isDisplayingTitleDialog = false,
                onTextChange = {},
                discardNote = {},
                startListening = {},
                stopListening = {},
                openTitleInputDialog = {},
                closeTitleInputDialog = {},
                saveNote = {}
            )
        }
    }
}

@Preview
@Composable
fun NoteEditorScreenListeningPreview() {
    MaterialTheme {
        Surface {
            NoteEditorScreen(
                spokenText = "Some text...",
                isSpeaking = true,
                isDisplayingTitleDialog = false,
                onTextChange = {},
                discardNote = {},
                startListening = {},
                stopListening = {},
                openTitleInputDialog = {},
                closeTitleInputDialog = {},
                saveNote = {}
            )
        }
    }
}

@Preview
@Composable
fun NoteEditorScreenDialogOpenedPreview() {
    MaterialTheme {
        Surface {
            NoteEditorScreen(
                spokenText = "Some text...",
                isSpeaking = false,
                isDisplayingTitleDialog = true,
                onTextChange = {},
                discardNote = {},
                startListening = {},
                stopListening = {},
                openTitleInputDialog = {},
                closeTitleInputDialog = {},
                saveNote = {}
            )
        }
    }
}
