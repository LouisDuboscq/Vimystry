package com.lduboscq.vimystry.android.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lduboscq.vimystry.GetPostsService
import com.lduboscq.vimystry.remote.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.Exception

class MainViewModel(
    private val getPostsService: GetPostsService
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
        object Pause : ViewEffect
    }

    private val currentState: ViewState
        get() = uiState.value

    private val _uiState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val uiState = _uiState.asStateFlow()

    private val _effect: Channel<ViewEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts = getPostsService()
                _uiState.value = currentState.copy(
                    posts = posts,
                    currentPost = posts.firstOrNull()
                )
            } catch (e: Exception) {
                viewModelScope.launch {
                    _effect.send(ViewEffect.Error(e.message ?: ""))
                }
            }
        }
    }

    fun userTappedRight() {
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

    fun userTappedLeft() {
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
            _effect.send(ViewEffect.Pause)
        }
    }
}
