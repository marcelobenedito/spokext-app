package com.github.marcelobenedito.spokext.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.marcelobenedito.spokext.ui.Note
import com.github.marcelobenedito.spokext.util.DateFormatUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    noteList: List<Note>,
    isRefreshing: Boolean,
    refreshNotes: () -> Unit,
    onSelectNote: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        refreshNotes()
    }

    if (isRefreshing)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Loading...",
                fontWeight = FontWeight.Bold
            )
        }

    if (noteList.isEmpty() && !isRefreshing)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "You didn't create a note yet.")
        }
    else
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(noteList) {
                Card(
                    onClick = { onSelectNote(it) },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Row {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.StickyNote2,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = it.title)
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = DateFormatUtil.formatTimestamp(it.date),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
}

@Preview
@Composable
fun NoteListScreenPreview() {
    MaterialTheme {
        Surface {
            NoteListScreen(
                noteList = emptyList(),
                isRefreshing = false,
                refreshNotes = {},
                onSelectNote = {}
            )
        }
    }
}

@Preview
@Composable
fun NoteListScreenNotEmptyPreview() {
    val noteList = listOf(
        Note(1, "Title 1", description = "Lorem ipsum dolor..."),
        Note(2, "Title 2", description = "Lorem ipsum dolor..."),
        Note(3, "Title 3", description = "Lorem ipsum dolor..."),
        Note(4, "Title 4", description = "Lorem ipsum dolor..."),
        Note(5, "Title 5", description = "Lorem ipsum dolor..."),
        Note(6, "Title 6", description = "Lorem ipsum dolor..."),
    )
    MaterialTheme {
        Surface {
            NoteListScreen(
                noteList = noteList,
                isRefreshing = false,
                refreshNotes = {},
                onSelectNote = {}
            )
        }
    }
}

@Preview
@Composable
fun NoteListScreenRefreshingPreview() {
    MaterialTheme {
        Surface {
            NoteListScreen(
                noteList = emptyList(),
                isRefreshing = true,
                refreshNotes = {},
                onSelectNote = {}
            )
        }
    }
}
