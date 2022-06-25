import com.lduboscq.vimystry.GetPostsService
import com.lduboscq.vimystry.android.home.MainViewModel
import com.lduboscq.vimystry.remote.Post
import com.lduboscq.vimystry.remote.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun userTappedLeftAndRightForEmptyListTest() {
        val viewModel = MainViewModel(getPostsService = createPostService(emptyList()))
        assertEquals(0, viewModel.uiState.value.currentPostIndex)
        viewModel.previousVideo()
        assertEquals(0, viewModel.uiState.value.currentPostIndex)
        viewModel.nextVideo()
        assertEquals(0, viewModel.uiState.value.currentPostIndex)
    }

    @Test
    fun userTappedRightWithAtTheEndTest() = runBlocking {
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
        val viewModel = MainViewModel(getPostsService = createPostService(listOf(post)))
        delay(300) // else init not called ?
        assertTrue(viewModel.uiState.value.posts.isNotEmpty())
        assertEquals(0, viewModel.uiState.value.currentPostIndex)
        assertEquals(post, viewModel.uiState.value.currentPost)
        viewModel.nextVideo()
        assertEquals(0, viewModel.uiState.value.currentPostIndex)
        assertEquals(post, viewModel.uiState.value.currentPost)
    }

    @Test
    fun userTappedLeftAndRightWithAListTest() = runBlocking {
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
        val viewModel = MainViewModel(getPostsService = createPostService(listOf(post, post2)))
        delay(300) // else init not called ?
        assertTrue(viewModel.uiState.value.posts.isNotEmpty())
        assertEquals(0, viewModel.uiState.value.currentPostIndex)
        assertEquals(post, viewModel.uiState.value.currentPost)
        viewModel.nextVideo()
        assertEquals(1, viewModel.uiState.value.currentPostIndex)
        assertEquals(post2, viewModel.uiState.value.currentPost)
        viewModel.previousVideo()
        assertEquals(0, viewModel.uiState.value.currentPostIndex)
        assertEquals(post, viewModel.uiState.value.currentPost)
    }

    private fun createPostService(list: List<Post>): GetPostsService = object : GetPostsService {
        override suspend fun invoke(): List<Post> = list
    }
}