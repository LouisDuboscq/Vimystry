package com.lduboscq.vimystry.android.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lduboscq.vimystry.android.SortPostsByDate
import com.lduboscq.vimystry.domain.Post
import com.lduboscq.vimystry.domain.PostsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val postsService: PostsService
) : ViewModel() {

    private val sortPostsByDate = SortPostsByDate()

    data class ViewState(
        val loading: Boolean = true,
        val currentPost: Post? = null,
        val posts: List<Post> = emptyList(),
        val pausedState: Boolean = false,
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

    private val index = MutableStateFlow(0)

    private val remotePosts: Flow<List<Post>> = postsService.pollPosts()
        .onEach { index.value = 0 }
        .map { sortPostsByDate(it) }
        .flowOn(Dispatchers.IO)
        .catch { exception ->
            viewModelScope.launch {
                _errors.send(ViewEffect.Error(exception.message ?: exception.javaClass.name))
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        remotePosts.combine(index) { posts, _index ->
            _uiState.value = currentState.copy(
                posts = posts,
                currentPost = try {
                    posts[_index]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                loading = false,
                pausedState = false
            )
        }.launchIn(viewModelScope)
    }

    fun nextVideo() {
        if (currentState.posts.isNotEmpty()
            && index.value != currentState.posts.size - 1
        ) {
            index.value = index.value + 1
        }
    }

    fun previousVideo() {
        var newIndex = index.value - 1
        if (newIndex < 0) {
            newIndex = 0
        }
        index.value = newIndex
    }

    fun userLongPressed() {
        _uiState.value =
            _uiState.value.copy(pausedState = !uiState.value.pausedState)
        viewModelScope.launch {
            _playPause.send(ViewEffect.PlayPause)
        }
    }
}
