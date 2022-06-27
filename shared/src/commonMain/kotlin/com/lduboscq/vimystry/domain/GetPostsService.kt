package com.lduboscq.vimystry.domain

import kotlinx.coroutines.flow.Flow

interface PostsService {
    suspend fun getPosts(): List<Post>
    fun pollPosts(): Flow<List<Post>>
}

