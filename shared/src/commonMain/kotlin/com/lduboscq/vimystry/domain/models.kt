package com.lduboscq.vimystry.domain

data class Post(
    val id: Long,
    val createdAt: String,
    val fileUrl: String,
    val likes: Long,
    val author: User
)

data class User(
    val id: Long,
    val avatar: String,
    val title: String
)
