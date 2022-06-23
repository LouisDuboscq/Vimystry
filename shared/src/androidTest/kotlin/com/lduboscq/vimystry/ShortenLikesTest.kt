package com.lduboscq.vimystry

import kotlin.test.Test
import kotlin.test.assertEquals

class ShortenLikesTest {
    private val shortenLikes = ShortenLikes()

    @Test
    fun test() {
        assertEquals("1,2k", shortenLikes(1200))
        assertEquals("2,4M", shortenLikes(2_356_000))
        assertEquals("4,0M", shortenLikes(4_000_988))
    }
}