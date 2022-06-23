package com.lduboscq.vimystry

import com.lduboscq.vimystry.remote.Post
import com.lduboscq.vimystry.remote.User

interface GetPostsService {
    suspend operator fun invoke(): List<Post>
}

class RealGetPostsService(/*val postsGraphQLApi: PostsGraphQLApi*/) : GetPostsService {
    override suspend operator fun invoke(): List<Post> {
        return listOf(
            Post(
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
        )
    }
/*
    override fun pollISSPosition(): Flow<IssPosition> {
        return flow {
            while (true) {
                val position = peopleInSpaceApi.fetchISSPosition().iss_position
                emit(position)
                logger.d { position.toString() }
                delay(POLL_INTERVAL)
            }
        }
    }
*/
    companion object {
        private const val POLL_INTERVAL = 10000L
    }

}
