package com.github.marcelobenedito.spokext.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicExternalOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.marcelobenedito.spokext.R
import com.github.marcelobenedito.spokext.data.VoiceToTextListenerState
import com.github.marcelobenedito.spokext.ui.screen.NoteEditorScreen
import com.github.marcelobenedito.spokext.ui.screen.NoteListScreen
import com.github.marcelobenedito.spokext.ui.viewmodel.SpokextViewModel

@Composable
fun SpokextApp(
    modifier: Modifier = Modifier,
    viewModel: SpokextViewModel = viewModel(factory = SpokextViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val uiState: VoiceToTextListenerState by viewModel.state.collectAsState()
    val noteList: List<Note> by viewModel.noteList.collectAsState()
    val editNote: Note? by viewModel.editNote.collectAsState()
    val isRefreshingNotes: Boolean by viewModel.isLoadingNotes.collectAsState()
    val isDisplayingTitleDialog: Boolean by viewModel.isDisplayingTitleDialog.collectAsState()
    val isDisplayingDiscardDialog: Boolean by viewModel.isDisplayingDiscardDialog.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SpokextScreen.valueOf(
        backStackEntry?.destination?.route ?: SpokextScreen.NoteList.name
    )

    Scaffold(
        topBar = { SpokextAppBar(currentScreen = currentScreen) },
        floatingActionButton = {
            if (currentScreen.name == SpokextScreen.NoteList.name)
            FloatingActionButton(
                onClick = { navController.navigate(SpokextScreen.NoteEditor.name) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = SpokextScreen.NoteList.name
        ) {
            composable(route = SpokextScreen.NoteList.name) {
                NoteListScreen(
                    noteList = noteList,
                    isRefreshing = isRefreshingNotes,
                    refreshNotes = viewModel::getAllNotes,
                    onSelectNote = {
                        viewModel.setNote(it)
                        navController.navigate(SpokextScreen.NoteEditor.name)
                    },
                    modifier = modifier.padding(padding)
                )
            }
            composable(route = SpokextScreen.NoteEditor.name) {
                NoteEditorScreen(
                    noteTitle = editNote?.title,
                    spokenText = uiState.spokenText,
                    isSpeaking = uiState.isSpeaking,
                    isDisplayingTitleDialog = isDisplayingTitleDialog,
                    isDisplayingDiscardDialog = isDisplayingDiscardDialog,
                    onTextChange = viewModel::onTextChange,
                    startListening = viewModel::startListening,
                    stopListening = viewModel::stopListening,
                    openTitleInputDialog = viewModel::openTitleInputDialog,
                    closeTitleInputDialog = viewModel::closeTitleInputDialog,
                    discardNote = {
                        viewModel.discardNote()
                        navController.navigateUp()
                    },
                    saveNote = {
                        viewModel.saveNote(it)
                        navController.navigateUp()
                    },
                    openDiscardDialog = viewModel::openDiscardDialog,
                    closeDiscardDialog = viewModel::closeDiscardDialog,
                    modifier = modifier.padding(padding)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpokextAppBar(
    currentScreen: SpokextScreen,
    modifier: Modifier = Modifier
) {
    if (currentScreen.name == SpokextScreen.NoteList.name)
        CenterAlignedTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(id = currentScreen.title),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.DarkGray
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = modifier
        )
}

enum class SpokextScreen(@StringRes val title: Int) {
    NoteList(title = R.string.app_name),
    NoteEditor(title = R.string.note_editor)
}
