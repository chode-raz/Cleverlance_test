package com.test.cleverlancetest.utils

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.security.MessageDigest

object EncodingUtils {

    suspend fun hashSHA1(inputString: String): String {
        return withContext(Dispatchers.IO) {
            val messageDigest = MessageDigest.getInstance("SHA-1")
            val inputByteArray = inputString.toByteArray()
            val digestedByteArray = messageDigest.digest(inputByteArray)
            BigInteger(1, digestedByteArray).toString(16)       //translate byte array into hexadecimal string
        }
    }

    suspend fun decodeImageString(base64EncodedImage: String): ByteArray {
        return withContext(Dispatchers.IO) {
            val decodedString: ByteArray = Base64.decode(base64EncodedImage, Base64.DEFAULT)
            decodedString
        }
    }

}