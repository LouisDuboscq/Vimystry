import com.lduboscq.vimystry.android.home.MainViewModel
import com.lduboscq.vimystry.domain.Post
import com.lduboscq.vimystry.domain.PostsService
import com.lduboscq.vimystry.domain.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun userTappedLeftAndRightForEmptyListTest() {
        val viewModel = MainViewModel(postsService = createPostService(emptyList()))
        assertNull(viewModel.uiState.value.currentPost)
        viewModel.previousVideo()
        assertNull(viewModel.uiState.value.currentPost)
        viewModel.nextVideo()
        assertNull(viewModel.uiState.value.currentPost)
    }

    @Test
    fun userTappedRightWithAtTheEndTest() = runTest {
        val post = Post(
            id = 0,
            createdAt = "2022-08-15T15:09:43Z",
            fileUrl = "https://cdn.jsdelivr.net/gh/theGlenn/fake-api@master/pexels-rodnae-productions-8034413.mp4",
            likes = 577687,
            author = User(
                id = 1,
                avatar = "https://i.pravatar.cc/150?u=74464",
                title = "d7zesKQgVL"
            )
        )
        val viewModel = MainViewModel(postsService = createPostService(listOf(post)))
        delay(300) // else init not called ?
        assertTrue(viewModel.uiState.first().posts.isNotEmpty())
        assertEquals(post, viewModel.uiState.first().currentPost)
        viewModel.nextVideo()
        assertEquals(post, viewModel.uiState.value.currentPost)
    }

    @Test
    fun userTappedLeftAndRightWithAListTest() = runTest {
        val post = Post(
            id = 0,
            createdAt = "2022-08-15T15:09:43Z",
            fileUrl = "https://cdn.jsdelivr.net/gh/theGlenn/fake-api@master/pexels-rodnae-productions-8034413.mp4",
            likes = 577687,
            author = User(
                id = 1,
                avatar = "https://i.pravatar.cc/150?u=74464",
                title = "d7zesKQgVL"
            )
        )
        val post2 = Post(
            id = 2,
            createdAt = "2022-08-15T15:09:43Z",
            fileUrl = "https://cdn.jsdelivr.net/gh/theGlenn/fake-api@master/pexels-rodnae-productions-8034413.mp4",
            likes = 57769,
            author = User(
                id = 2,
                avatar = "https://i.pravatar.cc/150?u=74464",
                title = "d7zesKQgUL"
            )
        )
        val viewModel = MainViewModel(postsService = createPostService(listOf(post, post2)))
        delay(300) // else init not called ?
        assertTrue(viewModel.uiState.value.posts.isNotEmpty())
        assertEquals(post, viewModel.uiState.value.currentPost)
        viewModel.nextVideo()
        assertEquals(post2, viewModel.uiState.value.currentPost)
        viewModel.previousVideo()
        assertEquals(post, viewModel.uiState.value.currentPost)
    }

    private fun createPostService(list: List<Post>): PostsService = object : PostsService {
        override suspend fun getPosts(): List<Post> = list
        override fun pollPosts(): Flow<List<Post>> = flow {}
    }
}