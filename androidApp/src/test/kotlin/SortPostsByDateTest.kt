import com.lduboscq.vimystry.android.SortPostsByDate
import com.lduboscq.vimystry.remote.Post
import com.lduboscq.vimystry.remote.User
import org.junit.Test
import kotlin.test.assertEquals

class SortPostsByDateTest {

    private val sortPosts = SortPostsByDate()

    @Test
    fun test() {
        assertEquals(
            listOf(
                createPostWithDate("2021-08-15T15:09:43Z"),
                createPostWithDate("2021-09-15T15:09:43Z"),
                createPostWithDate("2022-08-15T15:09:43Z"),
                createPostWithDate("2023-10-15T15:09:43Z"),
                createPostWithDate("2023-10-27T11:09:43Z"),
                createPostWithDate("2023-10-27T15:09:43Z"),
            ),
            sortPosts(
                listOf(
                    createPostWithDate("2023-10-15T15:09:43Z"),
                    createPostWithDate("2022-08-15T15:09:43Z"),
                    createPostWithDate("2023-10-27T11:09:43Z"),
                    createPostWithDate("2021-08-15T15:09:43Z"),
                    createPostWithDate("2021-09-15T15:09:43Z"),
                    createPostWithDate("2023-10-27T15:09:43Z"),
                )
            )
        )
    }

    private fun createPostWithDate(date: String): Post {
        return Post(
            id = 0,
            createdAt = date,
            fileUrl = "irrelevant",
            likes = 1,
            author = User(
                id = 1,
                avatar = "irrelevant",
                title = "irrelevant"
            )
        )
    }
}