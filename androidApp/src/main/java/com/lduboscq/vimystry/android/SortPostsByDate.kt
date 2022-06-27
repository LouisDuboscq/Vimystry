package com.lduboscq.vimystry.android

import com.lduboscq.vimystry.domain.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SortPostsByDate {
    private val dateTimeStrToLocalDateTime: (String) -> LocalDateTime = {
        LocalDateTime.parse(it, DateTimeFormatter.ofPattern(PATTERN))
    }

    operator fun invoke(posts: List<Post>): List<Post> = posts.sortedBy { dateTimeStrToLocalDateTime(it.createdAt) }

    companion object {
        private const val PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}
