package com.lduboscq.vimystry.remote

import com.apollographql.apollo3.ApolloClient
import com.lduboscq.vimystry.GetPostsQuery
import com.lduboscq.vimystry.domain.Post
import com.lduboscq.vimystry.domain.PostsService
import com.lduboscq.vimystry.domain.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostsGraphQLApi : PostsService {

    private val apolloClient = ApolloClient.Builder()
        .serverUrl(SERVER_URL)
        .build()

    companion object {
        private const val SERVER_URL = "https://mockend.com/theGlenn/fake-api/graphql"
        private const val POLL_INTERVAL = 300_000L // 5 min
    }

    override suspend fun getPosts(): List<Post> {
        val response = apolloClient.query(GetPostsQuery()).execute()
        return response.dataAssertNoErrors
            .posts
            ?.mapNotNull { it?.toPost() }
            ?: throw NoBackendDataException()
    }

    override fun pollPosts(): Flow<List<Post>> {
        return flow {
            while (true) {
                val posts = getPosts()
                emit(posts)
                delay(POLL_INTERVAL)
            }
        }
    }
}

private fun GetPostsQuery.Post.toPost(): Post {
    return Post(
        id = id?.toLongOrNull() ?: throw CannotParseBackendDataException(),
        createdAt = createdAt ?: throw CannotParseBackendDataException(),
        fileUrl = fileURL ?: throw CannotParseBackendDataException(),
        likes = likes?.toLong() ?: throw CannotParseBackendDataException(),
        author = User(
            id = author?.id?.toLongOrNull() ?: throw CannotParseBackendDataException(),
            title = author.name ?: throw CannotParseBackendDataException(),
            avatar = author.avatar ?: throw CannotParseBackendDataException(),
        )
    )
}

class CannotParseBackendDataException : Exception()
class NoBackendDataException : Exception()
