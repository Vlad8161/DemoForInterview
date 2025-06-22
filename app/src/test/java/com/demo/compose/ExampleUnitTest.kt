package com.demo.compose

import com.demo.compose.utils.toStringWithWhitespace
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `Test int with whitespace format`() {
        assertEquals("25", 25.toStringWithWhitespace())
        assertEquals("525", 525.toStringWithWhitespace())
        assertEquals("2 525", 2525.toStringWithWhitespace())
        assertEquals("32 525", 32525.toStringWithWhitespace())
        assertEquals("432 525", 432525.toStringWithWhitespace())
        assertEquals("5 432 525", 5432525.toStringWithWhitespace())
        assertEquals("15 432 525", 15432525.toStringWithWhitespace())
        assertEquals("215 432 525", 215432525.toStringWithWhitespace())
        assertEquals("1 215 432 525", 1215432525.toStringWithWhitespace())
    }
}