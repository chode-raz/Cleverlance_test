package com.test.cleverlancetest

import com.test.cleverlancetest.utils.EncodingUtils
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.charset.StandardCharsets

class EncryptionTest {

    @Test
    fun `test password encryption`() {
        val input = "test"
        val expectedHash = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3"

        runBlocking {
            val actualHash = EncodingUtils.hashSHA1(input)
            assertEquals(expectedHash, actualHash)
        }
    }

    @Test
    fun `test image decoding`() {
        val input = "dGVzdA=="
        val expectedString = "test"

        runBlocking {
            val actualString = EncodingUtils.decodeImageString(input).toString(StandardCharsets.UTF_8)
            assertEquals(expectedString, actualString)
        }
    }
}