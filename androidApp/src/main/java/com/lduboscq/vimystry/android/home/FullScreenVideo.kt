package com.lduboscq.vimystry.android.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.lduboscq.vimystry.domain.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun FullScreenVideo(post: Post) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.Builder()
                    .setUri(post.fileUrl)
                    /* .setMediaId(post.id.toString())
                     .setTag(post.id)
                     .setMediaMetadata(
                         MediaMetadata.Builder()
                             .setDisplayTitle(post.author.title)
                             .build()
                     )*/
                    .build()
            )

            playWhenReady = true
            this.prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        }
    )
}

@Composable
fun VideoPlayer(
    post: Post,
    onMediaFinished: () -> Unit,
    flow: Flow<MainViewModel.ViewEffect.PlayPause>
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    LaunchedEffect(Unit) {
        flow.onEach {
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
        }.collect()
    }

    LaunchedEffect(post) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(
                MediaItem.Builder()
                    .setUri(post.fileUrl)
                    .build()
            )

        exoPlayer.addListener(object : Player.Listener {
            @Deprecated("Deprecated in Java")
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    onMediaFinished()
                }
            }
        })
        exoPlayer.playWhenReady = true
        exoPlayer.prepare(source)
    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
            }
        }
    )
}
