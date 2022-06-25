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
            ),
            Post(
                id = 3,
                createdAt = "2021-08-15T15:09:43Z",
                fileUrl = "https://vod-eurosport.akamaized.net/ebu-au/2019/12/08/snookfinish-1269077-700-512-288.mp4",
                likes = 577687,
                author = User(
                    id = 2,
                    avatar = "https://i.pravatar.cc/150?u=74465",
                    title = "zdoubceaoi"
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
