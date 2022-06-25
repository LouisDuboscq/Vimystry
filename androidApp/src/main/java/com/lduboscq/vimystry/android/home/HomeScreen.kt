package com.lduboscq.vimystry.android.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.errors.filterIsInstance<MainViewModel.ViewEffect.Error>()
            .onEach {
                snackbarHostState.showSnackbar("Error happened : ${it.message}")
            }.collect()
    }

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .combinedClickable(
                onClick = {},
                onLongClick = { viewModel.userLongPressed() },
            )
            .background(Color.Black)
    ) {
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
            .clickable { viewModel.previousVideo() })

        Box(modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
            .clickable { viewModel.nextVideo() }
            .align(Alignment.CenterEnd))

        uiState.currentPost?.let {
            VideoPlayer(
                it,
                onMediaFinished = { viewModel.nextVideo() },
                viewModel.playPause
            )
        }

        AnimatedVisibility(
            !uiState.pausedState,
            enter = expandVertically() + expandIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = uiState.currentPost?.author?.avatar ?: "",
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
                Spacer(Modifier.padding(4.dp))
                Text(
                    uiState.currentPost?.likes?.toString() ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Red)
                )
                Spacer(Modifier.padding(2.dp))
                Icon(Icons.Rounded.Favorite, null, tint = Color.Red)
            }
        }
    }
}
