package com.lduboscq.vimystry.android.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.onEach {
            when (it) {
                MainViewModel.ViewEffect.Pause -> {
                    // todo pause video player
                }
                is MainViewModel.ViewEffect.Error -> {
                    snackbarHostState.showSnackbar("Error happened : ${it.message}")
                }
            }
        }.collect()
    }

    Column(modifier = Modifier.padding(paddingValues)) {
        Text(uiState.currentPost?.toString() ?: "")

        Text("Hello")

        Row {
            IconButton(onClick = { viewModel.userTappedLeft() }) {
                Icon(Icons.Filled.ArrowLeft, null)
            }

            IconButton(onClick = { viewModel.userLongPressed() }) {
                Icon(Icons.Filled.CenterFocusStrong, null)
            }

            IconButton(onClick = { viewModel.userTappedRight() }) {
                Icon(Icons.Filled.ArrowRight, null)
            }
        }

        AnimatedVisibility(!uiState.pausedState) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = uiState.currentPost?.author?.avatar ?: "",
                    contentDescription = null
                )
                Text(uiState.currentPost?.likes?.toString() ?: "")
            }
        }
    }
}
