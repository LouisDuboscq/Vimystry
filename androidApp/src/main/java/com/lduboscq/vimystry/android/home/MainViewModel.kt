package com.lduboscq.vimystry.android.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lduboscq.vimystry.domain.Post
import com.lduboscq.vimystry.domain.PostsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.Exception

class MainViewModel(
    private val postsService: PostsService
) : ViewModel() {

    data class ViewState(
        val loading: Boolean = false,
        val currentPost: Post? = null,
        val pausedState: Boolean = false,
        var currentPostIndex: Int = 0,
        val posts: List<Post> = emptyList()
    )

    sealed interface ViewEffect {
        data class Error(val message: String) : ViewEffect
        object PlayPause : ViewEffect
    }

    private val currentState: ViewState
        get() = uiState.value

    private val _uiState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val uiState = _uiState.asStateFlow()

    private val _playPause: Channel<ViewEffect.PlayPause> = Channel()
    val playPause = _playPause.receiveAsFlow()

    private val _errors: Channel<ViewEffect.Error> = Channel()
    val errors = _errors.receiveAsFlow()
/*
    val uiState = postsService.pollPosts()
        .map {
            ViewState(
                loading = false,
                pausedState = false,
                currentPost = it.firstOrNull(),
                posts = it,
                currentPostIndex = 0
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewState())
*/
/*
    data class PauseAndIndex(
        val pausedState: Boolean = false,
        val currentPostIndex: Int = 0
    )

    private val pauseAndIndex = MutableStateFlow(PauseAndIndex())

    private val remotePosts: Flow<List<Post>> = postsService.pollPosts()
        .onEach {
            pauseAndIndex.value = pauseAndIndex.value.copy(
                currentPostIndex = 0,
                pausedState = false
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        remotePosts.combine(pauseAndIndex) { posts, pai ->
            _uiState.value = currentState.copy(
                posts = posts,
                currentPost = posts[pai.currentPostIndex],
                loading = false
            )
        }*/
    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts: List<Post> = postsService.getPosts()
                _uiState.value = currentState.copy(
                    posts = posts,
                    currentPost = posts.firstOrNull()
                )
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _errors.send(ViewEffect.Error(e.message ?: ""))
                }
            }
        }
    }

    fun nextVideo() {
        if (currentState.posts.isNotEmpty() &&
            currentState.currentPostIndex != currentState.posts.size - 1
        ) {
            val newIndex = currentState.currentPostIndex + 1
            _uiState.value = currentState.copy(
                currentPostIndex = newIndex,
                currentPost = currentState.posts[newIndex]
            )
        }
    }

    fun previousVideo() {
        var newIndex = currentState.currentPostIndex - 1
        if (newIndex < 0) {
            newIndex = 0
        }

        val post = try {
            currentState.posts[newIndex]
        } catch (e: IndexOutOfBoundsException) {
            null
        }

        _uiState.value = currentState.copy(
            currentPostIndex = newIndex,
            currentPost = post
        )
    }

    fun userLongPressed() {
        _uiState.value = currentState.copy(pausedState = !currentState.pausedState)
        viewModelScope.launch {
            _playPause.send(ViewEffect.PlayPause)
        }
    }
}
